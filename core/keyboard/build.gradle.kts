plugins {
    id(Plugins.LIFTING_ANDROID_FEATURE)
    id(Plugins.LIFTING_ANDROID_LIBRARY_COMPOSE)
}

android { namespace = "com.lifting.app.core.keyboard" }

dependencies {
    coreDatastore()
}