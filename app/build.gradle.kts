import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.apache.commons.io.output.ByteArrayOutputStream

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(BuildLibs.COMPILE_SDK)

    defaultConfig {
        applicationId = "com.kvlg.emojify"
        minSdkVersion(BuildLibs.MIN_SDK)
        targetSdkVersion(BuildLibs.TARGET_SDK)
        compileSdkVersion(BuildLibs.COMPILE_SDK)
        versionCode = BuildLibs.versionCodeMobile
        versionName = BuildLibs.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        getByName(AppConfig.BuildTypes.DEBUG.name) {
            keyPassword = "android"
            storePassword = "android"
            keyAlias = "androiddebugkey"
            storeFile = File(projectDir, "debug.keystore")
        }
    }

    buildTypes {
        maybeCreate(AppConfig.BuildTypes.DEBUG.name).apply {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "+${getLastCommitHash()}"
            signingConfig = signingConfigs.getByName(AppConfig.BuildTypes.DEBUG.name)
        }
        maybeCreate(AppConfig.BuildTypes.DEV.name).apply {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "+${getLastCommitHash()}"
            signingConfig = signingConfigs.getByName(AppConfig.BuildTypes.DEBUG.name)
        }
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }

        configureEach {
            val appMetricaApiKey = gradleLocalProperties(rootDir).getProperty("app_metrica_api_key")
            buildConfigField("String", "APP_METRICA_API_KEY", appMetricaApiKey)
        }
    }

    kotlinOptions {
        this.jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":FluidLayout"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Libs.ACTIVITY_KTX)
    implementation(Libs.ADAPTER_DELEGATES)
    implementation(Libs.ADAPTER_DELEGATES_VIEW_BINDING)
    implementation(Libs.APPCOMPAT)
    implementation(Libs.APP_METRICA)
    implementation(Libs.CONSTRAINT_LAYOUT)
    implementation(Libs.CORE_KTX)
    implementation(Libs.COROUTINES)
    implementation(Libs.FRAGMENT_KTX)
    implementation(Libs.GSON)
    implementation(Libs.HILT_ANDROID)
    implementation(Libs.HILT_VIEWMODEL)
    implementation(Libs.KOTLIN_STDLIB)
    implementation(Libs.LIFECYCLE_LIVE_DATA_KTX)
    implementation(Libs.LIFECYCLE_VIEW_MODEL_KTX)
    implementation(Libs.LOTTIE)
    implementation(Libs.MATERIAL)
    implementation(Libs.PLAY_CORE_KTX)
    implementation(Libs.PREFERENCES)
    implementation(Libs.ROOM_KTX)
    implementation(Libs.ROOM_RUNTIME)
    implementation(Libs.ROOM_COMMON)

    kapt(Libs.ANDROIDX_HILT_COMPILER)
    kapt(Libs.HILT_COMPILER)
    kapt(Libs.ROOM_COMPILER)
}

fun getLastCommitHash(): String {
    val stdout = ByteArrayOutputStream()
    exec {
        commandLine("git", "rev-parse", "--short", "HEAD")
        standardOutput = stdout
    }
    @Suppress("DEPRECATION")
    return stdout.toString().trim()
}