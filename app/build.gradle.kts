plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.bcodegen"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.bcodegen"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }

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
    implementation (libs.androidx.room.runtime)
    annotationProcessor (libs.androidx.room.compiler)
    implementation (libs.androidx.room.ktx)


    implementation (libs.barcode.scanning.v1730)
    implementation (libs.play.services.mlkit.barcode.scanning)


    implementation (libs.zxing.android.embedded) // Latest stable version
    implementation (libs.core)// Core library


// Barcode Scanning (Chosen: ML Kit)
    implementation(libs.mlkit.barcode.scanning)
    implementation(libs.mlkit.vision.common)

// Camera (Chosen: CameraX)
    implementation(libs.camera.core)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)

// UI & Compatibility
    implementation(libs.appcompat)
    implementation(libs.material.v190) // Latest Material Design
    implementation(libs.constraintlayout)

// Activity & Fragment Support
    implementation(libs.activity.v170) // Chosen latest Activity API
    implementation(libs.fragment)

// PDF & Document Processing
    implementation(libs.itext7.core) // iText for PDFs

// Firebase (Using BoM for version compatibility)
    implementation(platform(libs.firebase.bom))
    implementation(libs.google.firebase.auth)

// Testing Dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

