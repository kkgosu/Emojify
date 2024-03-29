import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestedExtension
import com.android.build.gradle.internal.dsl.BuildType
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id(BuildLibs.RELEASE_HUB_PLUGIN) version "3.1.0"
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(BuildLibs.GRADLE_PLUGIN)
        classpath(BuildLibs.KOTLIN_PLUGIN)
        classpath(BuildLibs.HILT_PLUGIN)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    afterEvaluate {
        extensions
            .findByType(TestedExtension::class.java)
            ?.apply {
                configureBuildTypes()
                with(compileOptions) {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }
            }
    }
}

// Set build types for android module.
fun TestedExtension.configureBuildTypes() {

    fun BuildType.configProguard(isLibrary: Boolean): BuildType {
        return if (isLibrary) {
            consumerProguardFile(file("proguard-rules.pro"))
        } else {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    val isLibrary = this is LibraryExtension

    buildTypes {
        maybeCreate(AppConfig.BuildTypes.RELEASE.name).apply {
            isMinifyEnabled = true
            isDebuggable = false
            configProguard(isLibrary)
        }
        maybeCreate(AppConfig.BuildTypes.DEBUG.name).apply {
            isMinifyEnabled = false
            isDebuggable = true
            configProguard(isLibrary)
        }
        maybeCreate(AppConfig.BuildTypes.DEV.name).apply {
            isDebuggable = true
            isMinifyEnabled = true
            configProguard(isLibrary)
        }
    }
}

releasesHub {
    autoDetectDependenciesPaths = true
    //dependenciesPaths = listOf("buildSrc/src/main/kotlin/Libs.kt", "buildSrc/src/main/kotlin/BuildLibs.kt")
    excludes = listOf("gradle")

    gitHubRepository = "Emojify"

    pullRequestEnabled = true

    gitHubRepositoryOwner = "kkgosu"
    gitHubRepositoryName = "Emojify"

    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())

    gitHubWriteToken = properties.getProperty("git_token")
}