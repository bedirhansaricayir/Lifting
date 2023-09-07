buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(BuildPlugins.androidGradlePlugin)
        classpath(BuildPlugins.kotlinGradlePlugin)
        classpath(BuildPlugins.hiltPlugin)
        classpath(BuildPlugins.googleServices)
        classpath(BuildPlugins.crashlytics)
    }
}
plugins {
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}