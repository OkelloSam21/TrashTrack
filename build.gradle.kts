buildscript {
    dependencies {
        classpath(libs.google.services)
        classpath(libs.secrets.gradle.plugin)
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.devToolsKsp) apply false
//    id("com.google.devtools.ksp") version "1.9.23-1.0.20" apply false
    alias(libs.plugins.daggerHiltAndroid) apply false
}