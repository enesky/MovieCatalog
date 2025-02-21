import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "dev.enesky.buildlogic.convention"

java {
    sourceCompatibility = JavaVersion.toVersion(libs.versions.jvm.get())
    targetCompatibility = JavaVersion.toVersion(libs.versions.jvm.get())
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = libs.versions.jvm.get().toString()
}

dependencies {
    compileOnly(libs.android.plugin)
    compileOnly(libs.compose.plugin)
    compileOnly(libs.kotlin.plugin)
    compileOnly(libs.ksp.plugin)
    compileOnly(libs.detekt.plugin)

    enableVersionCatalogAccess()
}

gradlePlugin {
    plugins {
        val rootPath = "$group.plugins"

        /**
         * Application module Related Convention Plugins
         */
        register("appMain") {
            id = libs.plugins.app.main.get().pluginId
            implementationClass = "$rootPath.app.AppMainPlugin"
        }
        register("appFirebase") {
            id = libs.plugins.app.firebase.get().pluginId
            implementationClass = "$rootPath.app.AppFirebasePlugin"
        }

        /**
         * Library module Related Convention Plugins
         */
        register("libraryMain") {
            id = libs.plugins.library.main.get().pluginId
            implementationClass = "$rootPath.library.LibraryMainPlugin"
        }
        register("libraryFeature") {
            id = libs.plugins.library.feature.get().pluginId
            implementationClass = "$rootPath.library.LibraryFeaturePlugin"
        }

        /**
         * Common Convention Plugins
         */
        register("commonCompose") {
            id = libs.plugins.common.compose.get().pluginId
            implementationClass = "$rootPath.common.ComposePlugin"
        }
        register("commonHilt") {
            id = libs.plugins.common.hilt.get().pluginId
            implementationClass = "$rootPath.common.HiltPlugin"
        }
        register("commonTest") {
            id = libs.plugins.common.test.get().pluginId
            implementationClass = "$rootPath.common.TestPlugin"
        }
        register("commonApiKeyProvider") {
            id = libs.plugins.common.api.key.provider.get().pluginId
            implementationClass = "$rootPath.common.ApiKeyProviderPlugin"
        }
        register("commonGitHooks") {
            id = libs.plugins.common.git.hooks.get().pluginId
            implementationClass = "$rootPath.common.GitHooksPlugin"
        }
        register("commonDetekt") {
            id = libs.plugins.common.detekt.get().pluginId
            implementationClass = "$rootPath.common.DetektPlugin"
        }
        register("moduleDependencyGraph") {
            id = libs.plugins.common.dependency.graph.get().pluginId
            implementationClass = "$rootPath.common.ModuleDependencyGraphPlugin"
        }
    }
}

/**
 *                          !!! IMPORTANT !!!
 *
 *  This is needed in order to properly use libs extension in convention plugins
 *  and use convention plugins from version catalog in other modules
 *  without any additional configuration.
 *
 *  Gives access to the libs extension in the project
 *  internal val Project.libs
 *      get() = the<LibrariesForLibs>()
 */
fun DependencyHandlerScope.enableVersionCatalogAccess() {
    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}