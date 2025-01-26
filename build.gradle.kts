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
    id(BuildPlugins.KSP_PLUGIN) version "2.0.0-1.0.23" apply false
    kotlin(BuildPlugins.SERIALIZATION_PLUGIN) version "1.9.22" apply false
    id(BuildPlugins.COMPOSE_PLUGIN) version "2.0.0" apply false
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}