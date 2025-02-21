package dev.enesky.buildlogic.convention.plugins.common

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.Exec
import org.gradle.kotlin.dsl.register

/**
 * Configure Git Hooks
 * -> Only for project/build.gradle.kts <-
 */
class GitHooksPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        tasks.register("copyGitHooks", Copy::class.java) {
            description = "Copies the git hooks from /git-hooks to the .git folder."
            group = "Git Hooks"
            from("${project.rootDir}/tools/git-hooks/") {
                include("**/*.sh")
                rename { it.replace(".sh", "") }
            }
            into("${project.rootDir}/.git/hooks")
        }

        tasks.register("installGitHooks", Exec::class.java) {
            description = "Installs the git hooks from /tools/git-hooks/."
            group = "Git Hooks"
            workingDir = project.rootDir
            commandLine = listOf("chmod")
            setArgs(listOf("-R", "+x", ".git/hooks/"))
            dependsOn("copyGitHooks")
            doLast {
                project.logger.info("Git hooks installed successfully.")
            }
        }

        tasks.register("clean", Delete::class) {
            delete(rootProject.layout.buildDirectory)
        }

        afterEvaluate {
            tasks.named("clean") {
                dependsOn(":installGitHooks")
            }
        }
    }
}
