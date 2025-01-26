plugins {
    id(Plugins.LIFTING_ANDROID_LIBRARY)
    id(Plugins.LIFTING_HILT)
    id(Plugins.LIFTING_ROOM)
}

android { namespace = "com.lifting.app.core.database" }

dependencies {
    coreModel()
    coreCommon()
}