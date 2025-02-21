package dev.enesky.buildlogic.convention.plugins.library

import dev.enesky.buildlogic.convention.helpers.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * A plugin that applies common dependencies for feature modules.
 * -> For modules/build.gradle.kts, not for app/build.gradle.kts <-
 */
class LibraryFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {

        pluginManager.apply(libs.plugins.android.library.get().pluginId)

        dependencies {
            /** Todo: Add common dependencies for feature modules here
             *             implementation(project(":core:domain"))
             *             implementation(project(":core:common"))
             *             implementation(project(":core:design-system"))
             *             implementation(project(":core:navigation"))
             *             implementation(project(":core:ui"))
             */
        }
    }
}
