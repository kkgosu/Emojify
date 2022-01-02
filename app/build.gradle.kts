import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.apache.commons.io.output.ByteArrayOutputStream

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = BuildLibs.COMPILE_SDK

    defaultConfig {
        applicationId = "com.kvlg.emojify"
        minSdk = BuildLibs.MIN_SDK
        targetSdk = BuildLibs.TARGET_SDK
        compileSdk = BuildLibs.COMPILE_SDK
        versionCode = BuildLibs.versionCodeMobile
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

        configureEach {
            val appMetricaApiKey = gradleLocalProperties(rootDir).getProperty("app_metrica_api_key")
            buildConfigField("String", "APP_METRICA_API_KEY", appMetricaApiKey)
        }
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        this.jvmTarget = "11"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Libs.Compose.compose_version
    }
}

dependencies {
    implementation(project(":FluidLayout"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Libs.APP_METRICA)
    implementation(Libs.COROUTINES)
    implementation(Libs.GSON)
    implementation("com.google.dagger:hilt-android:2.38.1")
    implementation(Libs.HILT_COMPOSE_VIEWMODEL)
    implementation(Libs.KOTLIN_STDLIB)
    implementation(Libs.LIFECYCLE_COMPOSE_VIEWMODEL)
    implementation(Libs.LIFECYCLE_LIVE_DATA_KTX)
    implementation(Libs.LIFECYCLE_RUNTIME)
    implementation(Libs.LIFECYCLE_VIEW_MODEL_KTX)
    implementation(Libs.LOTTIE_COMPOSE)
    implementation(Libs.MATERIAL)
    implementation(Libs.PLAY_CORE_KTX)
    implementation(Libs.PREFERENCES)
    implementation(Libs.ROOM_KTX)
    implementation(Libs.ROOM_RUNTIME)
    implementation(Libs.ROOM_COMMON)

    implementation(Libs.Compose.ACTIVITY)
    implementation(Libs.Compose.COIL)
    implementation(Libs.Compose.ICONS_EXTENDED)
    implementation(Libs.Compose.INSETS)
    implementation(Libs.Compose.MATERIAL)
    implementation(Libs.Compose.RUNTIME_LIVEDATA)
    implementation(Libs.Compose.UI)
    implementation(Libs.Compose.PAGER)
    implementation(Libs.Compose.PAGER_INDICATOR)
    implementation(Libs.Compose.SYSTEM_UI_CONTROLLER)
    implementation(Libs.Compose.TOOLING)
    implementation(Libs.Compose.TOOLING_PREVIEW)

    kapt("com.google.dagger:hilt-android-compiler:2.38.1")
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