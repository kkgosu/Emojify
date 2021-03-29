object BuildLibs {
    val versionName = "1.5.8"
    private val versionCodeBase = 158
    val versionCodeMobile = versionCodeBase + 3

    const val COMPILE_SDK = 30
    const val TARGET_SDK = 30
    const val MIN_SDK = 21

    const val GRADLE_PLUGIN = "com.android.tools.build:gradle:4.1.1"
    const val KOTLIN_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.20"
    const val RELEASE_HUB_PLUGIN = "com.dipien.releaseshub.gradle.plugin"
    const val HILT_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:2.28-alpha"
}
