plugins {
    id(Plugins.LIFTING_ANDROID_LIBRARY)
    id(Plugins.LIFTING_ANDROID_LIBRARY_COMPOSE)
}
android { namespace = "com.lifting.app.core.ui" }

dependencies {
    coreDesignSystem()
    coreModel()
    coreBase()
    coreCommon()
    api(ThirdParty.collapsingToolbar)
    api(ThirdParty.coil)
    api(Compose.material)
    api(Androidx.appcompat)

}