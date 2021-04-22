import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestedExtension
import com.android.build.gradle.internal.dsl.BuildType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id(BuildLibs.RELEASE_HUB_PLUGIN) version "2.0.1"
}

buildscript {
    repositories {
        google()
        jcenter()
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
        jcenter()
    }
}

// Set build types for android module.
fun TestedExtension.configureBuildTypes() {

    fun BuildType.configProguard(isLibrary: Boolean): BuildType {
        return if (isLibrary) {
            consumerProguardFile(file("proguard-rules.pro"))
        } else {
            proguardFiles(
                file("proguard-rules.pro"),
                getDefaultProguardFile("proguard-android-optimize.txt")
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
            isMinifyEnabled = true
            isDebuggable = false
            configProguard(isLibrary)
        }
        maybeCreate(AppConfig.BuildTypes.DEV.name).apply {
            isDebuggable = true
        }
    }
}

subprojects {
    afterEvaluate {
        extensions
            .findByType(TestedExtension::class.java)
            ?.apply {
                configureBuildTypes()

                sourceSets.forEach { sourceSet ->
                    sourceSet.java.srcDir("src/${sourceSet.name}/kotlin")
                }

                with(compileOptions) {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }
            }
    }

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