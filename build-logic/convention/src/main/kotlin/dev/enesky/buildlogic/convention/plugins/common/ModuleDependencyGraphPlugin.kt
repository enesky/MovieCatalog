package dev.enesky.buildlogic.convention.plugins.common

import dev.enesky.buildlogic.convention.plugins.common.GraphConstants.MERMAID_FENCE_LENGTH
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import java.io.File

/**
 * Plugin to generate a mermaid dependency graph for multi-module project
 * Adds its result to README.md file
 *
 * Run with -> ./gradlew generateModuleDependencyGraph --no-configuration-cache
 **/
class ModuleDependencyGraphPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = with(project) {
        tasks.register("generateModuleDependencyGraph") {
            group = "documentation"
            description = "Generates a mermaid dependency graph for multi-module project"
            outputs.upToDateWhen { false }

            doLast {
                println("------------------------------------------------------------------------------------")
                println("Running Module Dependency Graph Plugin...")

                val dependencyTree = linkedMapOf<String, ElementNode>()
                rootProject.getDependencyNodes(dependencyTree)

                val moduleGroups = detectModuleGroups()

                val dotFile = File("$rootDir/.tools/dependency-graph/mermaid-graph.dot")
                dotFile.generateDotFile(dependencyTree, moduleGroups)

                val readme = File("$rootDir/README.md")
                val mermaidContent = dotFile.readText()
                updateReadmeFile(mermaidContent, readme.absolutePath)

                println("Module Dependency Graph Result:")
                println("-> Open $readme to see the module dependency graph")
                println("------------------------------------------------------------------------------------")
            }
        }
    }

    /**
     * Auto-detect module groups based on project paths
     */
    private fun Project.detectModuleGroups(): List<ModuleGroup> {
        val groups = GraphConstants.ModuleGroupType.values()
            .associateWith { mutableListOf<String>() }
            .toMutableMap()

        rootProject.subprojects
            .filter { it.path.split(":").size > 1 }
            .filter { it.name !in setOf("core", "feature") }
            .forEach { project ->
                val path = project.path.split(":")
                val groupType = GraphConstants.ModuleGroupType.fromPath(path)
                groups[groupType]?.add(project.name)
            }

        return groups
            .filter { it.value.isNotEmpty() }
            .map { ModuleGroup(it.key.groupName, it.value.sorted()) }
            .sortedBy { group ->
                GraphConstants.ModuleGroupType.values()
                    .first { it.groupName == group.name }
                    .order
            }
    }
}

/**
 * Collect all dependency nodes for the project
 */
private fun Project.getDependencyNodes(treeMap: MutableMap<String, ElementNode>) {
    fun collectNode(treeMap: MutableMap<String, ElementNode>, moduleName: String): ElementNode {
        return treeMap.getOrPut(moduleName) {
            ElementNode().apply { this.moduleName = moduleName }
        }
    }

    val queue: MutableList<Project> = mutableListOf(this)
    while (queue.isNotEmpty()) {
        val firstItem = queue.removeAt(0)
        val currentNode = collectNode(treeMap, firstItem.name)
        queue.addAll(firstItem.childProjects.values)
        firstItem.configurations.all { config ->
            config.dependencies
                .withType(ProjectDependency::class.java)
                .map { it.dependencyProject }
                .forEach { dependency ->
                    if (firstItem.name != dependency.name) {
                        val childNode = collectNode(treeMap, dependency.name)
                        if (currentNode.dependencyNode.none { it.moduleName == childNode.moduleName }) {
                            currentNode.dependencyNode.add(childNode)
                        }
                    }
                }
            true
        }
    }
}

/**
 * Generate dot file for mermaid graph
 */
private fun File.generateDotFile(
    treeMap: MutableMap<String, ElementNode>,
    moduleGroups: List<ModuleGroup>
) {
    parentFile.mkdirs()

    // Add theme configs and main graph definition
    writeText(GraphConstants.THEME_CONFIG.trimIndent() + "\n")
    appendText("graph TD\n")

    // Add subgraphs for module groups
    moduleGroups.forEach { group ->
        appendText("    subgraph ${group.name}\n")
        group.modules.forEach { module ->
            appendText("        $module\n")
        }
        appendText("    end\n")
    }

    // Add dependencies
    treeMap.forEach { (_, element) ->
        element.dependencyNode.forEach { childElement ->
            val sourceModule = element.moduleName.split(":").last()
            val targetModule = childElement.moduleName.split(":").last()
            appendText("    $sourceModule-->$targetModule\n")
        }
    }

    appendText("\n%% Auto generated by ModuleDependencyGraphPlugin.kt\n")
}

/**
 * Find the end of the current mermaid section in README
 */
private fun findMermaidSectionEnd(content: String, startIndex: Int): Int {
    val mermaidStart = content.indexOf("```mermaid", startIndex)
    if (mermaidStart == -1) return content.length

    val mermaidEnd = content.indexOf("```", mermaidStart + 10)
    if (mermaidEnd == -1) return content.length

    return mermaidEnd + MERMAID_FENCE_LENGTH
}

/**
 * Update README.md file with the mermaid content while preserving content after the graph section
 */
private fun updateReadmeFile(mermaidContent: String, readmePath: String) {
    val readmeFile = File(readmePath)
    if (!readmeFile.exists()) {
        readmeFile.createNewFile()
        val newContent = buildString {
            appendLine(GraphConstants.README_SECTION_HEADER)
            appendLine()
            appendLine("```mermaid")
            appendLine(mermaidContent)
            appendLine("```")
            appendLine()
        }
        readmeFile.writeText(newContent)
        println("-> README.md created with module dependency graph!")
        return
    }

    val content = readmeFile.readText()
    val sectionStart = content.indexOf(GraphConstants.README_SECTION_HEADER)

    val newContent = if (sectionStart == -1) {
        // If section doesn't exist, append everything
        buildString {
            append(content)
            if (!content.endsWith("\n")) appendLine()
            appendLine()
            appendLine(GraphConstants.README_SECTION_HEADER)
            appendLine()
            appendLine("```mermaid")
            appendLine(mermaidContent)
            appendLine("```")
            appendLine()
        }
    } else {
        // Find where the current mermaid section ends
        val mermaidSectionEnd = findMermaidSectionEnd(content, sectionStart)

        // Get the content after the current mermaid section
        val contentAfter = content.substring(mermaidSectionEnd).trim()

        // Construct new content
        buildString {
            append(content.substring(0, sectionStart))
            appendLine(GraphConstants.README_SECTION_HEADER)
            appendLine()
            appendLine("```mermaid")
            appendLine(mermaidContent)
            appendLine("```")
            if (contentAfter.isNotEmpty()) {
                appendLine()
                appendLine(contentAfter)
            }
        }
    }

    readmeFile.writeText(newContent)
    println("-> README.md updated successfully!")
}

data class ElementNode(
    var moduleName: String = "",
    var dependencyNode: MutableList<ElementNode> = mutableListOf(),
)

data class ModuleGroup(
    val name: String,
    val modules: List<String>,
)

private object GraphConstants {
    const val README_SECTION_HEADER = "## Module Dependency Graph"
    const val MERMAID_FENCE_LENGTH = 3 // Length of "```" fence markers
    const val THEME_CONFIG = """
        %%{
            init: {
            'theme': 'base',
            'themeVariables': {
                "primaryTextColor":"#ffffff",
                "primaryColor":"#5a4f7c",
                "primaryBorderColor":"#ffffff",
                "tertiaryBorderColor":"#40375c",
                "lineColor":"#f5a623",
                "tertiaryColor":"#40375c",
                "fontSize":"14px"
                }
            }
        }%%
    """

    enum class ModuleGroupType(val groupName: String, val order: Int) {
        APPLICATION("Application Modules", 1),
        CORE("Core Modules", 2),
        FEATURE("Feature Modules", 3);

        companion object {
            fun fromPath(path: List<String>): ModuleGroupType = when {
                path.contains("core") -> CORE
                path.contains("feature") -> FEATURE
                else -> APPLICATION
            }
        }
    }
}
