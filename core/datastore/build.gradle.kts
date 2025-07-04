plugins {
    id(Plugins.LIFTING_ANDROID_LIBRARY)
    id(Plugins.LIFTING_HILT)
}

android { namespace = "com.lifting.app.core.datastore" }

dependencies {
    coreModel()
    api(Androidx.datastore)
}