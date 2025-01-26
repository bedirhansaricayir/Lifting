plugins {
    id(Plugins.LIFTING_ANDROID_LIBRARY)
}

android { namespace = "com.lifting.app.core.base" }

dependencies {
    api(Kotlin.ktxViewModel)
}