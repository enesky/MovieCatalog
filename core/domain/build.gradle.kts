plugins {
    id(libs.plugins.library.main.get().pluginId)
    id(libs.plugins.common.hilt.get().pluginId)
}

android.namespace = "dev.enesky.core.domain"

dependencies {
    // Modules
    implementation(projects.core.data)
    implementation(projects.core.network)
    implementation(projects.core.common)

    // Libraries
    implementation(libs.paging.main)
    implementation(libs.paging.compose)
}