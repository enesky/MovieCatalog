package dev.enesky.buildlogic.convention.plugins.common

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import dev.enesky.buildlogic.convention.helpers.implementation
import dev.enesky.buildlogic.convention.helpers.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

/**
 * Configure Compose for modules
 */
class ComposePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            pluginManager.apply {
                apply(libs.plugins.kotlin.compose.get().pluginId)
                apply(libs.plugins.kotlin.serialization.get().pluginId)
            }
            extensions.findByType(ApplicationExtension::class.java)?.apply {
                configureCompose(this)
            }
            extensions.findByType(LibraryExtension::class.java)?.apply {
                configureCompose(this)
            }
            configureComposeCompilerMetrics()
        }
    }
}

/**
 * Configure Compose-specific options
 */
private fun Project.configureCompose(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    with(commonExtension) {
        apply(plugin = libs.plugins.kotlin.compose.get().pluginId)

        buildFeatures.compose = true

        dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(platform(libs.androidx.compose.bom))
            implementation(libs.androidx.ui.main)
            implementation(libs.androidx.ui.graphics)
            implementation(libs.androidx.ui.tooling)
            implementation(libs.androidx.ui.tooling.preview)
            implementation(libs.androidx.material3)

            implementation(libs.accompanist.placeholder)
            implementation(libs.accompanist.systemuicontroller)
            implementation(libs.coil.compose)
            implementation(libs.coil.network)
        }
    }
}

/**
 * Configure compose compiler metrics
 * to enable compose metrics:
 * ./gradlew moduleName:assembleDebug -PcomposeCompilerReports=true
 * then check the path:
 * {moduleName}/build/compose_compiler
 **/
private fun Project.configureComposeCompilerMetrics() {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        project.findProperty("composeCompilerReports")?.toString()?.toBoolean()?.let { enabled ->
            if (enabled) {
                val path = project.layout.buildDirectory.dir("compose_compiler").get().asFile.absolutePath
                compilerOptions.freeCompilerArgs.addAll(
                    "-P", "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=$path",
                    "-P", "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$path"
                )
            }
        }
    }
}
