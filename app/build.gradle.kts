plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.betterkrishi"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.betterkrishi"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        mlModelBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.compose.material:material:1.6.7")
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("androidx.compose.material:material-icons-extended:1.6.7")
    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth")
    implementation ("io.coil-kt:coil-compose:2.6.0")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Gson Converter for Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Lifecycle components
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
//    implementation("androidx.compose.material:material:1.6.7")
    // Make sure to include this for LiveData integration
    implementation ("androidx.compose.runtime:runtime-livedata:1.6.7")
    implementation("com.google.android.exoplayer:exoplayer-core:2.19.1")
    implementation("com.google.android.exoplayer:exoplayer-ui:2.19.1")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    // Accompanist for additional features like loading images
    implementation("com.google.accompanist:accompanist-coil:0.15.0")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("com.google.accompanist:accompanist-permissions:0.35.0-alpha")
    implementation("androidx.compose.material3:material3")
    implementation("org.tensorflow:tensorflow-lite-support:0.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.tensorflow:tensorflow-lite-metadata:0.1.0")
    implementation("com.google.firebase:firebase-appcheck-playintegrity:18.0.0")
    implementation("com.google.firebase:firebase-appcheck-ktx:18.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.05.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}