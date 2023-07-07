object BuildPlugins {

    val androidGradlePlugin by lazy { "com.android.tools.build:gradle:${Versions.gradleVersion}" }
    val kotlinGradlePlugin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinGradlePluginVersion}" }
    val hiltPlugin by lazy { "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltAndroidPluginVersion}" }

}