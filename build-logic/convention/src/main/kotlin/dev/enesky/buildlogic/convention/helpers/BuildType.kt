package dev.enesky.buildlogic.convention.helpers

/**
 * Created by Enes Kamil YILMAZ on 21/02/2025
 */
enum class BuildType(
    val applicationIdSuffix: String? = null,
) {
    DEBUG(".debug"),
    RELEASE,
}
