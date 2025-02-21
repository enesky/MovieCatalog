package dev.enesky.buildlogic.convention.plugins.common

import dev.enesky.buildlogic.convention.helpers.detektPlugins
import dev.enesky.buildlogic.convention.helpers.libs
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import java.io.File

/**
 * Detekt Configurations
 *  to use it on normal mode -> ./gradlew detektAll
 *  to use it with auto correct enabled -> ./gradlew detektAll / detektAllDebug --auto-correct
 *  to use it to create a baseline -> ./gradlew detektCreateBaseline / detektBaselineDebug
 **/
class DetektPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        val sourcePath: String = rootDir.absolutePath
        val mainConfigFile = "$rootDir/tools/detekt/config.yml"
        val composeConfigFile = "$rootDir/tools/detekt/compose-config.yml"
        val baselineFile = "$rootDir/tools/detekt/baseline.xml"
        val kotlinFiles = "**/*.kt"
        val resourceFiles = "**/resources/**"
        val buildFiles = "**/build/**"
        val generatedFiles = "**/generated/**"
        val ideRelatedFiles = "**/.idea/**"

        pluginManager.apply {
            apply(libs.plugins.detekt.plugin.get().pluginId)
        }

        dependencies {
            detektPlugins(libs.detekt.formatting.get())
            detektPlugins(libs.detekt.compose.rules.get())
            detektPlugins(libs.detekt.compose.appkode.get())
        }

        extensions.configure<DetektExtension> {
            tasks.withType<Detekt>().configureEach {
                include(kotlinFiles)
                exclude(resourceFiles, buildFiles, generatedFiles, ideRelatedFiles)
            }

            tasks.register<Detekt>("detektAll") {
                description = "Detekts all detekt issues."
                setSource(sourcePath)
                config.setFrom(files(mainConfigFile, composeConfigFile))
                baseline.set(File(baselineFile))
                parallel = true
            }

            tasks.register<DetektCreateBaselineTask>("detektCreateBaseline") {
                description = "Creates a new detekt baseline."
                parallel.set(true)
                ignoreFailures.set(true)
                setSource(sourcePath)
                config.setFrom(files(mainConfigFile, composeConfigFile))
                baseline.set(File(baselineFile))
                include(kotlinFiles)
                exclude(resourceFiles, buildFiles, generatedFiles, ideRelatedFiles)
            }
        }
    }
}
