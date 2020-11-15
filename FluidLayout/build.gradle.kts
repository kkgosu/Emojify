plugins {
    id("com.android.library")
    kotlin("android")
}
android {
    compileSdkVersion(BuildLibs.COMPILE_SDK)

    defaultConfig {
        minSdkVersion(BuildLibs.MIN_SDK)
        targetSdkVersion(BuildLibs.TARGET_SDK)
        versionCode = BuildLibs.versionCodeMobile
        versionName = BuildLibs.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.incremental"] = "true"
            }
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
    implementation(Libs.APPCOMPAT)
    implementation(Libs.KOTLIN_STDLIB)
}