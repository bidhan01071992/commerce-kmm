import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    kotlin("native.cocoapods")
    alias(libs.plugins.androidLibrary)
    id("com.apollographql.apollo") version "4.3.3"
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation("com.apollographql.apollo:apollo-runtime:4.3.3")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }

    cocoapods {
        summary = "Shared module for MyApp"
        homepage = "https://example.com"
        ios.deploymentTarget = "13.0"
        version = "1.0.0"  // 👈 Add this line

        framework {
            baseName = "Shared"
            isStatic = false
        }
    }
}

android {
    namespace = "com.example.kmmecomproject.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

apollo {
    service("service") {
        packageName.set("com.example.kmmecommerce.graphql")
    }
}
