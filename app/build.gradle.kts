plugins {
    id(libs.plugins.app.main.get().pluginId)
    id(libs.plugins.common.compose.get().pluginId)
    id(libs.plugins.common.hilt.get().pluginId)
    id(libs.plugins.app.firebase.get().pluginId)
    id(libs.plugins.app.signing.config.get().pluginId)
}

android {
    namespace = "dev.enesky.moviecatalog"

    defaultConfig {
        applicationId = "dev.enesky.moviecatalog"

        versionCode = libs.versions.version.code.get().toInt()
        versionName = libs.versions.version.name.get()
    }
}

dependencies {
    // Module dependencies
    implementation(projects.core.ui)
    implementation(projects.core.common)
    implementation(projects.core.navigation)
}
