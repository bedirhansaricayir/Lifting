plugins {
    id(Plugins.LIFTING_ANDROID_FEATURE)
    id(Plugins.LIFTING_ANDROID_LIBRARY_COMPOSE)
}

android { namespace = "com.lifting.app.feature.calendar" }

dependencies {
    implementation("androidx.paging:paging-runtime:3.3.6")
    implementation("androidx.paging:paging-compose:3.3.6")
}