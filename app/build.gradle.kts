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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    // Core dependencies
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // UI Theming (Catppuccin)
    implementation(libs.catppuccin.palette.legacy)
    implementation(libs.catppuccin.palette)
    implementation(libs.catppuccin.compose)
    implementation(libs.catppuccin.splashscreen)

    // Glide for image loading
    implementation(libs.glide.lib)
    annotationProcessor(libs.glide.annotation)

    // Room Database
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    // Unit testing
    testImplementation(libs.junit)
    testImplementation(libs.mockito)
    testImplementation(libs.androidx.test.core)

    // Android Instrumentation testing
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.espresso.contrib)
    androidTestImplementation(libs.espresso.intents)
    androidTestImplementation(libs.espresso.accessibility)
    androidTestImplementation(libs.espresso.idling.concurrent)
    // has to be implementation because the resources used are not available in androidTestImplementation
    implementation(libs.espresso.idling.resource)

}
