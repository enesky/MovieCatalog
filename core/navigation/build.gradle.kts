plugins {
    id(libs.plugins.library.main.get().pluginId)
    id(libs.plugins.common.compose.get().pluginId)
    id(libs.plugins.common.hilt.get().pluginId)
}

android.namespace = "dev.enesky.core.navigation"

dependencies {
    // Module dependencies
    implementation(projects.core.ui)
    implementation(projects.feature.home)
    implementation(projects.feature.detail)
    implementation(projects.feature.player)

    // Library dependencies

}
