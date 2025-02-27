plugins {
    id(libs.plugins.library.main.get().pluginId)
    id(libs.plugins.common.hilt.get().pluginId)
}

android.namespace = "dev.enesky.core.datastore"

dependencies {
    // Modules
    implementation(projects.core.domain)

    // Libraries
    implementation(libs.datastore.preferences)
}
