@file:Suppress("DEPRECATION")

package dev.enesky.buildlogic.convention.helpers

import com.android.build.api.dsl.CommonExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import java.util.Properties

internal val Project.libs
    get() = the<LibrariesForLibs>()

// Todo: Update the deprecated function
internal fun CommonExtension<*, *, *, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) =
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)

internal fun getLocalProperties(rootProject: Project): Properties {
    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties") // Note: It's ignored by git
    if (localPropertiesFile.exists() && localPropertiesFile.isFile) {
        localPropertiesFile.inputStream().use { input ->
            localProperties.load(input)
        }
    }
    return localProperties
}

// Dependency Handler helpers
internal fun DependencyHandlerScope.implementation(dependency: Any) = "implementation"(dependency)
internal fun DependencyHandlerScope.testImplementation(dependency: Any) = "testImplementation"(dependency)
internal fun DependencyHandlerScope.androidTestImplementation(dependency: Any) = "androidTestImplementation"(dependency)
internal fun DependencyHandlerScope.ksp(dependency: Any) = "ksp"(dependency)
internal fun DependencyHandlerScope.detektPlugins(dependency: Any) = "detektPlugins"(dependency)
internal fun DependencyHandlerScope.releaseImplementation(dependency: Any) = "releaseImplementation"(dependency)
internal fun DependencyHandlerScope.debugImplementation(dependency: Any) = "debugImplementation"(dependency)
internal fun DependencyHandlerScope.coreLibraryDesugaring(dependency: Any) = "coreLibraryDesugaring"(dependency)
