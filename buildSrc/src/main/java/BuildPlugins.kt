object BuildPlugins {
    private const val gradleVersion = "8.4.0"
    private const val kotlinGradlePluginVersion = "2.0.0"
    private const val hiltAndroidPluginVersion = "2.48"

    val androidGradlePlugin by lazy { "com.android.tools.build:gradle:${gradleVersion}" }
    val kotlinGradlePlugin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinGradlePluginVersion}" }
    val hiltPlugin by lazy { "com.google.dagger:hilt-android-gradle-plugin:${hiltAndroidPluginVersion}" }
    const val ANDROID_APPLICATION_PLUGIN = "com.android.application"
    const val ANDROID_LIBRARY_PLUGIN = "com.android.library"
    const val KOTLIN_ANDROID_PLUGIN = "kotlin-android"
    const val DAGGER_HILT = "com.google.dagger.hilt.android"
    const val KSP_PLUGIN = "com.google.devtools.ksp"
    const val SERIALIZATION = "kotlinx-serialization"
    const val KOTLIN_JVM_PLUGIN = "org.jetbrains.kotlin.jvm"
    const val SERIALIZATION_PLUGIN = "plugin.serialization"
    const val KOTLIN_PARCELIZE = "kotlin-parcelize"
    const val COMPOSE_PLUGIN = "org.jetbrains.kotlin.plugin.compose"
}
object Plugins {
    const val LIFTING_ANDROID_APPLICATION = "lifting.android.application"
    const val LIFTING_ANDROID_APPLICATION_COMPOSE = "lifting.android.application.compose"
    const val LIFTING_ANDROID_FEATURE = "lifting.android.feature"
    const val LIFTING_ANDROID_LIBRARY_COMPOSE = "lifting.android.library.compose"
    const val LIFTING_ANDROID_LIBRARY = "lifting.android.library"
    const val LIFTING_HILT = "lifting.hilt"
    const val LIFTING_JVM_LIBRARY = "lifting.jvm.library"
    const val LIFTING_ROOM = "lifting.room"
    const val LIFTING_NETWORK = "lifting.network"
}