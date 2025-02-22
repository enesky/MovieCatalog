plugins {
    id(libs.plugins.library.main.get().pluginId)
    id(libs.plugins.common.compose.get().pluginId)
    id(libs.plugins.common.hilt.get().pluginId)
}

android.namespace = "dev.enesky.core.ui"

dependencies {
    // Module dependencies
    implementation(projects.core.common)
}
