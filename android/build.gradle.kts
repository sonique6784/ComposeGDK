plugins {
    id("org.jetbrains.compose") version "1.0.0"
    id("com.android.application")
    kotlin("android")
}

group = "me.sonique"
version = "1.0"

repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("org.openrndr:openrndr-math:0.3.58")

}

android {
    compileSdkVersion(31)
    defaultConfig {
        applicationId = "me.sonique.android"
        minSdkVersion(24)
        targetSdkVersion(31)
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

        lintOptions {
            // TODO Enable Lint once everything is fixed
            isCheckReleaseBuilds = false
            //If you want to continue even if errors found use following line
            isAbortOnError = false
        }

}