import java.util.Properties
import java.io.FileInputStream

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
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

        }
    }
}

composeCompiler {
    enableStrongSkippingMode = true
}

dependencies {
    coreDesignSystem()
    coreNavigation()

    featureExercises()
    featureCreateExercise()
    featureExercisesCategory()
    featureExercisesMuscle()
    featureWorkout()
    featureWorkoutEdit()

    api(Androidx.splash)
}