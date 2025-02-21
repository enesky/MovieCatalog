package dev.enesky.buildlogic.convention.plugins.app

import dev.enesky.buildlogic.convention.helpers.implementation
import dev.enesky.buildlogic.convention.helpers.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Firebase plugin for the application module
 */
class AppFirebasePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.android.application.get().pluginId)
                apply(libs.plugins.google.services.get().pluginId)
                apply(libs.plugins.firebase.performance.get().pluginId)
                apply(libs.plugins.firebase.crashlytics.get().pluginId)
            }

            dependencies {
                implementation(platform(libs.firebase.bom))
                implementation(libs.firebase.analytics)
                implementation(libs.firebase.crashlytics)
                implementation(libs.firebase.performance)
            }
        }
    }
}
