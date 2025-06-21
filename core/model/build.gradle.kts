plugins {
    id(Plugins.LIFTING_ANDROID_LIBRARY)
    id(BuildPlugins.KOTLIN_PARCELIZE)
    id(BuildPlugins.SERIALIZATION)
}

android { namespace = "com.lifting.app.core.model" }

dependencies {
    api(Kotlin.kotlinSerializationJson)
}