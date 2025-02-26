plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.room)
}

android {
    namespace = "no.dat153.quizzler"
    compileSdk = 35

    room {
        schemaDirectory("$projectDir/schemas")
    }

    defaultConfig {
        applicationId = "no.dat153.quizzler"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    implementation(libs.catppuccin.palette.legacy)
    implementation(libs.catppuccin.palette)
    implementation(libs.catppuccin.compose)
    implementation(libs.catppuccin.splashscreen)

    implementation(libs.glide.lib)
    annotationProcessor(libs.glide.annotation)

    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
