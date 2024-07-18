object BuildPlugins {

    val androidGradlePlugin by lazy { "com.android.tools.build:gradle:${Versions.gradleVersion}" }
    val kotlinGradlePlugin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinGradlePluginVersion}" }
    val hiltPlugin by lazy { "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltAndroidPluginVersion}" }
    val googleServices by lazy { "com.google.gms:google-services:${Versions.googleServicesPluginVersion}" }
    val crashlytics by lazy { "com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlyticsPluginVersion}" }
    const val ANDROID_APPLICATION_PLUGIN = "com.android.application"
    const val ANDROID_LIBRARY_PLUGIN = "com.android.library"
    const val KOTLIN_ANDROID_PLUGIN = "kotlin-android"
    const val KOTLIN_KAPT = "kotlin-kapt"
    const val DAGGER_HILT = "com.google.dagger.hilt.android"
    const val KSP_PLUGIN = "com.google.devtools.ksp"
    const val SERIALIZATION = "kotlinx-serialization"
    const val KOTLIN_JVM_PLUGIN = "org.jetbrains.kotlin.jvm"
    const val KOTLIN_ANDROID_JB_PLUGIN = "org.jetbrains.kotlin.android"
    const val SERIALIZATION_PLUGIN = "plugin.serialization"
    const val KOTLIN_PARCELIZE = "kotlin-parcelize"


}