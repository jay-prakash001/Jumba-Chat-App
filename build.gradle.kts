// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
//
//    kotlin("jvm") version "2.1.0"
//    kotlin("plugin.serialization") version "2.1.0"


    id("com.google.gms.google-services") version "4.4.2" apply false
}