plugins {
    id(libs.plugins.library.main.get().pluginId)
    id(libs.plugins.common.compose.get().pluginId)
    id(libs.plugins.common.hilt.get().pluginId)
}

android.namespace = "dev.enesky.core.common"

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.config)
    implementation(libs.jank.stats)
}
