import java.util.Properties
import java.io.FileInputStream

@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("kotlin-parcelize")
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = AppConfig.APPLICATION_ID
    compileSdk = AppConfig.COMPILE_SDK

    defaultConfig {
        applicationId = AppConfig.APPLICATION_ID
        minSdk = AppConfig.MIN_SDK
        targetSdk = AppConfig.TARGET_SDK
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.testInstrumentationRunner
    }

    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }



    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.kotlinCompilerExtensionVersion
    }

    val freeCompilerArgList = listOf(
        "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
        "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
        "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
        "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
        "-opt-in=kotlin.Experimental",
        "-opt-in=kotlin.RequiresOptIn",
    )

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            freeCompilerArgs += freeCompilerArgList
        }
    }

}

dependencies {
    api(project(":core:network"))

    Kotlin.list.forEach(::api)
    Compose.list.forEach(::api)
    Di.list.forEach(::api)

    coreLibraryDesugaring(ThirdParty.desugaring)
    api(Androidx.splash)
}