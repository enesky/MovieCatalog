plugins {
    id(libs.plugins.library.main.get().pluginId)
    id(libs.plugins.common.compose.get().pluginId)
    id(libs.plugins.common.hilt.get().pluginId)
}

android.namespace = "dev.enesky.feature.home"

dependencies {
    // Module dependencies
    implementation(projects.core.ui)
    implementation(projects.core.domain)
    implementation(projects.core.common)
    implementation(projects.core.network)
}
