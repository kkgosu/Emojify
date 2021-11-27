plugins {
    id("com.android.library")
    kotlin("android")
}
android {
    compileSdk = BuildLibs.COMPILE_SDK

    defaultConfig {
        minSdk = BuildLibs.MIN_SDK
        targetSdk = BuildLibs.TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        this.jvmTarget = "11"
    }
}

dependencies {
    implementation(Libs.APPCOMPAT)
    implementation(Libs.KOTLIN_STDLIB)
}