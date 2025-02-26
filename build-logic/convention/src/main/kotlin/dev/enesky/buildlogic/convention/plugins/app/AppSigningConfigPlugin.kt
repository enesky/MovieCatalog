package dev.enesky.buildlogic.convention.plugins.app

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import dev.enesky.buildlogic.convention.helpers.createSigningConfigFromProperties
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.util.Properties

/**
 * Configure Android Signing Config-specific options
 *      -> Only for app/build.gradle.kts <-
 */
class AppSigningConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val keystoreProperties = Properties()
        val keystorePath = "../MovieCatalog/.keystore/keystore.properties"
        val keystorePropertiesFile = rootProject.file(keystorePath)
        if (keystorePropertiesFile.exists() && keystorePropertiesFile.isFile) {
            keystorePropertiesFile.inputStream().use { input ->
                keystoreProperties.load(input)
            }
        } else {
            throw IllegalArgumentException("Keystore properties file not found! Expected path: $keystorePath")
        }

        extensions.configure<BaseAppModuleExtension> {
            val debugSigningConfig = signingConfigs.getByName("debug")
            val releaseSigningConfig = createSigningConfigFromProperties(
                target = this@with,
                name = "release",
                properties = keystoreProperties,
            )

            defaultConfig {
                buildTypes {
                    release {
                        signingConfig = releaseSigningConfig ?: debugSigningConfig
                    }
                }
            }
        }
    }
}
