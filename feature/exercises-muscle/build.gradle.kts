plugins {
    id(BuildPlugins.ANDROID_LIBRARY_PLUGIN)
    id(BuildPlugins.KOTLIN_ANDROID_PLUGIN)
    id(BuildPlugins.DAGGER_HILT)
    id(BuildPlugins.KSP_PLUGIN)
}

android {
    namespace = "com.lifting.app.feature.exercises_muscle"
    compileSdk = AppConfig.COMPILE_SDK

    defaultConfig {
        minSdk = AppConfig.MIN_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.kotlinCompilerExtensionVersion
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}


dependencies {
    api(project(":core:base"))
    api(project(":core:navigation"))
    api(project(":core:ui"))
    api(project(":core:data"))


    Kotlin.list.forEach(::api)

    with(Di) {
        implementation(hiltAndroid)
        implementation(hiltNavigationCompose)
        ksp(hiltCompiler)
        ksp(hiltAndroidCompiler)
    }
}