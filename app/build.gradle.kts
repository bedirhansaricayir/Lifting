plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = ConfigData.applicationId
    compileSdk = ConfigData.compileSdk

    defaultConfig {
        applicationId = ConfigData.applicationId
        minSdk = ConfigData.mindSdk
        targetSdk = ConfigData.targetSdk
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

        testInstrumentationRunner = ConfigData.testInstrumentationRunner
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

        }
    }

    compileOptions {
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
        kotlinCompilerExtensionVersion  = Versions.kotlinCompilerExtensionVersion
    }
}

dependencies {
    implementation (Dependencies.coreKtx)
    implementation (platform(Dependencies.platformKotlin))
    implementation (Dependencies.lifecycleRuntimeKtx)
    implementation (Dependencies.compose)
    implementation (platform(Dependencies.platformCompose))
    implementation (Dependencies.composeUi)
    implementation (Dependencies.composeUiGraphics)
    implementation (Dependencies.composeUiPreview)
    implementation (Dependencies.material3)
    implementation (Dependencies.foundation)

    testImplementation (Dependencies.junit)
    androidTestImplementation (Dependencies.junitTest)
    androidTestImplementation (Dependencies.espressoCore)
    androidTestImplementation (platform(Dependencies.composeTest))
    androidTestImplementation (Dependencies.composeTestJunit4)
    debugImplementation (Dependencies.composeTestTooling)
    debugImplementation (Dependencies.composeTestManifest)

    implementation (Dependencies.composeViewModel)
    implementation (Dependencies.ktxViewModel)

    implementation (Dependencies.coroutinesCore)
    implementation (Dependencies.coroutinesAndroid)

    implementation (Dependencies.retrofit)
    implementation (Dependencies.gsonConvertor)
    implementation (Dependencies.okHttp)
    implementation (Dependencies.okHttpInterceptor)

    implementation (Dependencies.navigation)

    implementation (Dependencies.splash)

    implementation (Dependencies.hiltAndroid)
    kapt (Dependencies.hiltAndroidCompiler)
    implementation (Dependencies.hiltNavigationCompose)
    kapt (Dependencies.hiltCompiler)

    implementation (Dependencies.datastore)

    implementation (Dependencies.numberPicker)

    implementation(platform(Dependencies.platformFirebase))
    implementation(Dependencies.firebaseAuth)
    implementation(Dependencies.googleServicesAuth)
}