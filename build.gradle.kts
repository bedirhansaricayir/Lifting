plugins {
    id(BuildPlugins.application) version Versions.gradleVersion apply false
    id(BuildPlugins.library) version Versions.gradleVersion apply false
    id(BuildPlugins.kotlinPlugin) version Versions.kotlinGradlePluginVersion apply false
    id(BuildPlugins.hiltPlugin) version Versions.hiltAndroidPluginVersion apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}