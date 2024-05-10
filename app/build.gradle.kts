import java.util.Properties
import java.io.FileInputStream

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
}

dependencies {
    coreLibraryDesugaring(Dependencies.desugaring)

    implementation(Dependencies.coreKtx)
    implementation(platform(Dependencies.platformKotlin))
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.compose)
    implementation(platform(Dependencies.platformCompose))
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeUiGraphics)
    implementation(Dependencies.composeUiPreview)
    implementation(Dependencies.material3)
    implementation(Dependencies.foundation)

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.junitTest)
    androidTestImplementation(Dependencies.espressoCore)
    androidTestImplementation(platform(Dependencies.composeTest))
    androidTestImplementation(Dependencies.composeTestJunit4)
    debugImplementation(Dependencies.composeTestTooling)
    debugImplementation(Dependencies.composeTestManifest)

    implementation(Dependencies.composeViewModel)
    implementation(Dependencies.ktxViewModel)

    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesAndroid)

    implementation(Dependencies.retrofit)
    implementation(Dependencies.gsonConvertor)
    implementation(Dependencies.okHttp)
    implementation(Dependencies.okHttpInterceptor)

    implementation(Dependencies.navigation)

    implementation(Dependencies.splash)

    implementation(Dependencies.hiltAndroid)
    ksp(Dependencies.hiltAndroidCompiler)
    implementation(Dependencies.hiltNavigationCompose)
    ksp(Dependencies.hiltCompiler)

    implementation(Dependencies.datastore)

    implementation(Dependencies.numberPicker)

    implementation(platform(Dependencies.platformFirebase))
    implementation(Dependencies.firebaseAuth)
    implementation(Dependencies.crashlytics)
    implementation(Dependencies.firestore)
    implementation(Dependencies.storage)
    implementation(Dependencies.config)

    implementation(Dependencies.googleServicesAuth)

    implementation(Dependencies.coil)
    implementation(Dependencies.youtubePlayer)
    implementation(Dependencies.chart)
    implementation(Dependencies.room)
    ksp(Dependencies.roomCompiler)
    implementation(Dependencies.roomRuntime)

    implementation(Dependencies.calendar)

    implementation(Dependencies.media3)
    implementation(Dependencies.media3_exoplayer)

    implementation(Dependencies.inappUpdate)
    implementation(Dependencies.inappUpdateKtx)
}