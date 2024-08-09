buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(BuildPlugins.androidGradlePlugin)
        classpath(BuildPlugins.kotlinGradlePlugin)
        classpath(BuildPlugins.hiltPlugin)
    }
}

subprojects {
    val freeCompilerArgList = listOf(
        "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
        "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
        "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
        "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
        "-opt-in=kotlin.Experimental",
        "-opt-in=kotlin.RequiresOptIn",
        "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-Xcontext-receivers",
        "-opt-in=androidx.compose.animation.ExperimentalSharedTransitionApi",
        "-opt-in=androidx.compose.animation.ExperimentalSharedTransitionScope"
    )

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            freeCompilerArgs += freeCompilerArgList
        }
    }
}

plugins {
    id(BuildPlugins.KSP_PLUGIN) version "1.9.22-1.0.17" apply false
    id(BuildPlugins.KOTLIN_JVM_PLUGIN) version "1.9.22" apply false
    id(BuildPlugins.ANDROID_LIBRARY_PLUGIN) version "8.1.0" apply false
    id(BuildPlugins.KOTLIN_ANDROID_JB_PLUGIN) version "1.9.22" apply false
    kotlin(BuildPlugins.SERIALIZATION_PLUGIN) version "1.9.22" apply false
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}