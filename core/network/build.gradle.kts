plugins {
    id(Plugins.LIFTING_ANDROID_LIBRARY)
    id(Plugins.LIFTING_NETWORK)
    id(Plugins.LIFTING_HILT)
}

android {
    namespace = "com.lifting.app.core.network"

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"http://127.0.0.1:8080\"")
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    coreCommon()
    coreDatastore()
}