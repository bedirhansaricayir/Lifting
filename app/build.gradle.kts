import java.util.Properties
import java.io.FileInputStream

@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id(BuildPlugins.ANDROID_APPLICATION_PLUGIN)
    id(BuildPlugins.KOTLIN_ANDROID_PLUGIN)
    id(BuildPlugins.DAGGER_HILT)
    id(BuildPlugins.KSP_PLUGIN)
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
    api(project(":core:navigation"))
    api(project(":core:designsystem"))
    api(project(":core:data"))

    Kotlin.list.forEach(::api)
    Compose.list.forEach(::api)

    with(Di) {
        implementation(hiltAndroid)
        implementation(hiltNavigationCompose)
        ksp(hiltCompiler)
        ksp(hiltAndroidCompiler)
    }

    api(Androidx.splash)
    coreLibraryDesugaring(ThirdParty.desugaring)
}