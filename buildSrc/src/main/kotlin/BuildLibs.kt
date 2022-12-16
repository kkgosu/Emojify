object BuildLibs {
    val versionName = "1.7.0"
    private val versionCodeBase = 170
    val versionCodeMobile = versionCodeBase + 3

    const val COMPILE_SDK = 33
    const val TARGET_SDK = 33
    const val MIN_SDK = 21

    const val GRADLE_PLUGIN = "com.android.tools.build:gradle:7.3.1"
    const val KOTLIN_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10"
    const val RELEASE_HUB_PLUGIN = "com.dipien.releaseshub.gradle.plugin"
    const val HILT_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:2.43.2"
}
