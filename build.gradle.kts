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
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}