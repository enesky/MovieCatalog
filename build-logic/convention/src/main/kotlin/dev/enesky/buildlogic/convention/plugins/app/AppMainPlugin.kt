package dev.enesky.buildlogic.convention.plugins.app

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.AbstractAppExtension
import dev.enesky.buildlogic.convention.helpers.configureKotlinAndroid
import dev.enesky.buildlogic.convention.helpers.debugImplementation
import dev.enesky.buildlogic.convention.helpers.getBuildTypes
import dev.enesky.buildlogic.convention.helpers.implementation
import dev.enesky.buildlogic.convention.helpers.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import java.io.File

/**
 * Configure Android Application-specific options
 */
class AppMainPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.plugins.android.application.get().pluginId)
            apply(libs.plugins.kotlin.android.get().pluginId)
            apply(libs.plugins.kotlin.serialization.get().pluginId)

            // Convention Plugins
            apply(libs.plugins.common.test.get().pluginId)
            apply(libs.plugins.common.detekt.get().pluginId)
        }

        extensions.configure<ApplicationExtension> {
            defaultConfig {
                targetSdk = libs.versions.target.sdk.get().toString().toInt()
                buildFeatures.buildConfig = true

                getBuildTypes()

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

            configureKotlinAndroid(this)
        }

        // Add KSP source directories to the model to make them visible to Android Studio
        extensions.configure<AbstractAppExtension> {
            applicationVariants.all {
                addJavaSourceFoldersToModel(
                    File(layout.buildDirectory.asFile.get(), "generated/ksp/$name/kotlin"),
                )
            }
        }

        dependencies {
            debugImplementation(libs.leak.canary)
            implementation(libs.kotlinx.serialization)
        }
    }
}
