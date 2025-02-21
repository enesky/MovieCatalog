package dev.enesky.buildlogic.convention.helpers

import com.android.build.api.dsl.ApplicationExtension

internal fun ApplicationExtension.getBuildTypes() = buildTypes {
    debug {
        isDebuggable = true
        isMinifyEnabled = false
        isShrinkResources = false
        applicationIdSuffix = BuildType.DEBUG.applicationIdSuffix

        resValue("string", "app_name_flavor", "MovieCatalog -Debug")
        buildConfigField("boolean", "logEnabled", "true")
    }

    getByName("release") {
        isDebuggable = false
        isMinifyEnabled = true
        isShrinkResources = true
        applicationIdSuffix = BuildType.RELEASE.applicationIdSuffix
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )

        resValue("string", "app_name_flavor", "MovieCatalog")
        buildConfigField("boolean", "logEnabled", "false")
    }
}
