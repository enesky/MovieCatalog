plugins {
    id(libs.plugins.library.main.get().pluginId)
    id(libs.plugins.common.hilt.get().pluginId)
    id(libs.plugins.common.api.key.provider.get().pluginId)
}

android.namespace = "dev.enesky.core.network"

dependencies {
    // Modules
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.common)

    // Libraries
    implementation(libs.retrofit.main)
    implementation(libs.retrofit.converter)
    implementation(libs.paging.main)
    implementation(libs.paging.compose)
    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)
}