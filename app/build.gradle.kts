plugins {
    id("com.android.application")
    kotlin("android")
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
    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            versionNameSuffix = "-debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        this.jvmTarget = "1.8"
    }
}

dependencies {
    implementation(Libs.ACTIVITY_KTX)
    implementation(Libs.ADAPTER_DELEGATES)
    implementation(Libs.ADAPTER_DELEGATES_VIEW_BINDING)
    implementation(Libs.APPCOMPAT)
    implementation(Libs.CONSTRAINT_LAYOUT)
    implementation(Libs.CORE_KTX)
    implementation(Libs.COROUTINES)
    implementation(Libs.FRAGMENT_KTX)
    implementation(Libs.GSON)
    implementation(Libs.KOTLIN_STDLIB)
    implementation(Libs.LIFECYCLE_LIVE_DATA_KTX)
    implementation(Libs.LIFECYCLE_VIEW_MODEL_KTX)
    implementation(Libs.MATERIAL)
}