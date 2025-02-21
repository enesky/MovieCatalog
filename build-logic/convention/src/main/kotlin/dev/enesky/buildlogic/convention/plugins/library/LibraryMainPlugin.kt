package dev.enesky.buildlogic.convention.plugins.library

import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import dev.enesky.buildlogic.convention.helpers.configureKotlinAndroid
import dev.enesky.buildlogic.convention.helpers.disableUnnecessaryAndroidTests
import dev.enesky.buildlogic.convention.helpers.implementation
import dev.enesky.buildlogic.convention.helpers.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import java.io.File

/**
 * Android Library Main Convention Plugin
 * -> For modules/build.gradle.kts, not for app/build.gradle.kts <-
 */
class LibraryMainPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.android.library.get().pluginId)
                apply(libs.plugins.kotlin.android.get().pluginId)
                apply(libs.plugins.ksp.plugin.get().pluginId)
                apply(libs.plugins.kotlin.serialization.get().pluginId)

                // Convention Plugins
                apply(libs.plugins.common.test.get().pluginId)
                apply(libs.plugins.common.detekt.get().pluginId)
            }

            extensions.configure<LibraryExtension> {
                buildFeatures.buildConfig = true

                configureKotlinAndroid(this)

                defaultConfig {
                    packaging.resources.excludes.addAll(
                        listOf(
                            "/META-INF/{AL2.0,LGPL2.1}",
                            "/META-INF/LICENSE",
                            "/META-INF/LICENSE.md",
                            "/META-INF/NOTICE",
                            "/META-INF/NOTICE.md",
                            "/META-INF/LICENCE-notice.md",
                            "META-INF/LICENSE-notice.md"
                        )
                    )
                }

                // Add KSP source directories to the model to make them visible to Android Studio
                libraryVariants.all {
                    addJavaSourceFoldersToModel(
                        File(layout.buildDirectory.get().asFile, "generated/ksp/$name/kotlin"),
                    )
                }
            }

            extensions.configure<LibraryAndroidComponentsExtension> {
                disableUnnecessaryAndroidTests(target)
            }

            dependencies {
                implementation(libs.kotlinx.serialization)
            }
        }
    }
}
