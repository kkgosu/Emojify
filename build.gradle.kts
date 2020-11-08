import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id(BuildLibs.RELEASE_HUB_PLUGIN) version "1.6.1"
}

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(BuildLibs.GRADLE_PLUGIN)
        classpath(BuildLibs.KOTLIN_PLUGIN)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

subprojects {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions.freeCompilerArgs +=
            "-Xuse-experimental=" +
                    "kotlin.Experimental," +
                    "kotlinx.coroutines.ExperimentalCoroutinesApi," +
                    "kotlinx.coroutines.InternalCoroutinesApi," +
                    "kotlinx.coroutines.FlowPreview"
    }
}

releasesHub {
    dependenciesBasePath = "buildSrc/src/main/kotlin/"
    dependenciesClassNames = listOf("Libs.kt", "BuildLibs.kt")
    excludes = listOf("gradle")

    pullRequestEnabled = true

    gitHubRepositoryOwner = "kkgosu"
    gitHubRepositoryName = "Emojify"

    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())

    gitHubWriteToken = properties.getProperty("git_token")
}