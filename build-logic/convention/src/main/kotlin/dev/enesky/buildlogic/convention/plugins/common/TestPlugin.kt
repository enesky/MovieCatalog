package dev.enesky.buildlogic.convention.plugins.common

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import dev.enesky.buildlogic.convention.helpers.androidTestImplementation
import dev.enesky.buildlogic.convention.helpers.libs
import dev.enesky.buildlogic.convention.helpers.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.UnknownDomainObjectException
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension

/**
 * Configure Test libraries
 */
class TestPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        extensions.findByType(ApplicationExtension::class.java)?.apply {
            configureTestOptions(this)
        }
        extensions.findByType(LibraryExtension::class.java)?.apply {
            configureTestOptions(this)
        }

        tasks.withType<Test> {
            jvmArgs("--add-opens", "java.base/java.lang=ALL-UNNAMED")
            jvmArgs("--add-opens", "java.base/java.util=ALL-UNNAMED")
            jvmArgs("--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED")
        }.configureEach {
            try {
                configure<JacocoTaskExtension> {
                    isIncludeNoLocationClasses = true
                    // Required for JDK 11 with the above
                    excludes = listOf("jdk.internal.*")
                }
            } catch (e: UnknownDomainObjectException) {
                logger.info("Jacoco extension not found. Skipping Jacoco configuration.")
            }
        }

        dependencies {
            testImplementation(libs.junit)
            testImplementation(libs.mockk.main)
            testImplementation(libs.coroutines.test)
            testImplementation(libs.test.core.ktx)
            androidTestImplementation(libs.androidx.junit)
            androidTestImplementation(libs.androidx.espresso.core)
            androidTestImplementation(platform(libs.androidx.compose.bom))
            androidTestImplementation(libs.mockk.android)
        }
    }
}

/**
 * Configure Test options
 */
private fun configureTestOptions(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    with(commonExtension) {
        defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildTypes.getByName("debug").enableUnitTestCoverage = true
        testOptions.unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }
}
