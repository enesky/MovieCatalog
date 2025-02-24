plugins {
    id(libs.plugins.library.main.get().pluginId)
    id(libs.plugins.common.compose.get().pluginId)
    id(libs.plugins.common.hilt.get().pluginId)
}

android.namespace = "dev.enesky.feature.player"

dependencies {
    // Module dependencies
    implementation(projects.core.common)
    implementation(projects.core.domain)
    implementation(projects.core.ui)

    // Library dependencies

}
