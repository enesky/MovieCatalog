package dev.enesky.buildlogic.convention.plugins.common

import com.android.build.gradle.LibraryExtension
import dev.enesky.buildlogic.convention.helpers.getLocalProperties
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/**
 * Configure Api Key Provider
 * -> Only for core/network/build.gradle.kts <-
 */
class ApiKeyProviderPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val localProperties = getLocalProperties(rootProject)
        val baseApiUrl: String = checkNotNull(
            localProperties.getProperty("moviecatalog.api.url") ?: System.getenv("MOVIE_API_URL") ?: "\"\"",
        )
        val baseApiKey: String = checkNotNull(
            localProperties.getProperty("moviecatalog.api.key") ?: System.getenv("MOVIE_API_KEY") ?: "\"\"",
        )
        val baseImageUrl: String = checkNotNull(
            localProperties.getProperty("moviecatalog.image.url") ?: System.getenv("MOVIE_IMAGE_URL") ?: "\"\"",
        )

        with(extensions.getByType<LibraryExtension>()) {
            buildFeatures.buildConfig = true
            defaultConfig.buildConfigField("String", "MOVIE_API_URL", baseApiUrl)
            defaultConfig.buildConfigField("String", "MOVIE_API_KEY", baseApiKey)
            defaultConfig.buildConfigField("String", "MOVIE_IMAGE_URL", baseImageUrl)
        }
    }
}
