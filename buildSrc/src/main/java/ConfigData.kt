object AppConfig {
    private const val versionMajor = 1
    private const val versionMinor = 0
    private const val versionPatch = 0

    const val versionCode = versionMajor * 10000 + versionMinor * 100 + versionPatch
    const val versionName = "$versionMajor.$versionMinor.$versionPatch"


    const val COMPILE_SDK = 35
    const val MIN_SDK = 24
    const val TARGET_SDK = 35
    //val versionCode = 13
    //val versionName = "1.0.0"
    const val APPLICATION_ID = "com.lifting.app"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}