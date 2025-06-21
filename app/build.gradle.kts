import java.io.FileInputStream
import java.util.Properties

plugins {
    id(Plugins.LIFTING_ANDROID_APPLICATION)
    id(Plugins.LIFTING_ANDROID_APPLICATION_COMPOSE)
    id(Plugins.LIFTING_HILT)
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
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
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

        }
    }
}

dependencies {
    coreDesignSystem()
    coreNavigation()
    coreDatastore()
    coreCommon()
    coreModel()
    coreUI()
    coreKeyboard()
    coreService()

    featureBundle()

    api(Androidx.splash)
    api(Androidx.appcompat)
}