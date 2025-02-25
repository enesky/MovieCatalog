plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.detekt.plugin) apply false
    alias(libs.plugins.ksp.plugin) apply false
    alias(libs.plugins.hilt.plugin) apply false

    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.performance) apply false

    // Convention Plugins
    id(libs.plugins.common.detekt.get().pluginId)
    id(libs.plugins.common.git.hooks.get().pluginId)
    id(libs.plugins.common.dependency.graph.get().pluginId)
}