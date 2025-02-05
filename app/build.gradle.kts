plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.0.21"

    id("com.google.gms.google-services")
}

android {
    namespace = "com.jp.chatapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.jp.chatapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.text.google.fonts)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.kotlinx.serialization.json)


    // Jetpack Compose integration
    implementation(libs.androidx.navigation.compose)


    implementation (libs.socket.io.client)


    // Koin Android features
    implementation(libs.koin.android.v350)
    implementation(libs.koin.androidx.navigation)
    implementation(libs.koin.androidx.compose)

    implementation( libs.coil.compose)

    implementation(libs.ktor.client.android)
    implementation("androidx.datastore:datastore-preferences:1.1.2")
    implementation("io.ktor:ktor-client-cio:1.6.4")
//    implementation("io.ktor:ktor-client-content-negotiation:1.6.8")
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.logging)


    implementation(platform("com.google.firebase:firebase-bom:33.8.0"))



    implementation("com.google.firebase:firebase-messaging")
}