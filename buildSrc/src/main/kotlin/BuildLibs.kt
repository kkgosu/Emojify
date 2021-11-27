object BuildLibs {
    val versionName = "1.6.1"
    private val versionCodeBase = 161
    val versionCodeMobile = versionCodeBase + 3

    const val COMPILE_SDK = 31
    const val TARGET_SDK = 31
    const val MIN_SDK = 21

    const val GRADLE_PLUGIN = "com.android.tools.build:gradle:7.0.3"
    const val KOTLIN_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31"
    const val RELEASE_HUB_PLUGIN = "com.dipien.releaseshub.gradle.plugin"
    const val HILT_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:2.38.1"
}
