plugins {
    id(Plugins.LIFTING_ANDROID_LIBRARY)
    id(Plugins.LIFTING_HILT)
    id(BuildPlugins.SERIALIZATION)
}

android { namespace = "com.lifting.app.core.data" }

dependencies {
    coreModel()
    coreDatastore()
    coreDatabase()
    coreCommon()
    coreService()
    api(Kotlin.kotlinSerializationJson)

}