plugins {
    id(Plugins.LIFTING_ANDROID_LIBRARY)
    id(Plugins.LIFTING_ANDROID_LIBRARY_COMPOSE)
    id(BuildPlugins.SERIALIZATION)
}

android { namespace = "com.lifting.app.core.navigation" }

dependencies {
    coreDesignSystem()
    api(Compose.material)
    api(Compose.materialNavigation)
    api(Kotlin.kotlinSerializationJson)
}