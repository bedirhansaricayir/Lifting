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