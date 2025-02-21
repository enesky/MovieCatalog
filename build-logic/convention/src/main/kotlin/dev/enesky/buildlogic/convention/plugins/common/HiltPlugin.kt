package dev.enesky.buildlogic.convention.plugins.common

import dev.enesky.buildlogic.convention.helpers.implementation
import dev.enesky.buildlogic.convention.helpers.ksp
import dev.enesky.buildlogic.convention.helpers.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure Hilt for modules
 */
class HiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.ksp.plugin.get().pluginId)
                apply(libs.plugins.hilt.plugin.get().pluginId)
            }

            dependencies {
                ksp(libs.hilt.compiler)
                implementation(libs.hilt.android)
                implementation(libs.hilt.navigation.compose)
            }
        }
    }
}
