import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.0.0-rc3"
    id("com.android.library")
}

group = "me.sonique"
version = "1.0"

object Versions {
    const val compose = "1.0.0"
}

object Compose {
    const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
    const val ui = "androidx.compose.ui:ui:${Versions.compose}"
    const val uiGraphics = "androidx.compose.ui:ui-graphics:${Versions.compose}"
    const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val foundationLayout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
    const val material = "androidx.compose.material:material:${Versions.compose}"
    const val material_icons = "androidx.compose.material:material-icons-core:${Versions.compose}"
    const val runtimeLiveData = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
//    const val navigation = "androidx.navigation:navigation-compose:${Versions.nav_compose}"
//    const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:${Versions.constraint_compose}"
    //const val accompanist= "dev.chrisbanes.accompanist:accompanist-coil:${Versions.accompanist}"
    // Test rules and transitive dependencies:
    const val compose_ui_test_junit = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    const val compose_ui_test_manifest = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"

}

kotlin {
    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.ui)
                api(compose.desktop.currentOs)
                implementation("org.openrndr:openrndr-math:0.3.47")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.appcompat:appcompat:1.4.0")
                api("androidx.core:core-ktx:1.6.0")
                api(Compose.ui)
                api(Compose.runtime)
                //api(Compose.foundation)
                api(Compose.material)
                api(Compose.ui)
                //api(compose.desktop.currentOs)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val desktopMain by getting {
            dependencies {
                api(compose.preview)
            }
        }
        val desktopTest by getting
    }
}

android {
    compileSdkVersion(31)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(30)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}