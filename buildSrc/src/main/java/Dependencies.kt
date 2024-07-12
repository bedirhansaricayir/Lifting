object Kotlin {
    private val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtxVersion}" }
    private val platformKotlin by lazy { "org.jetbrains.kotlin:kotlin-bom:${Versions.platformKotlinBomVersion}" }
    private val coroutinesCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCoreVersion}" }
    private val coroutinesAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroidVersion}" }
    private val lifecycleRuntimeKtx by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtxVersion}" }
    val ktxViewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewmodelKtxVersion}" }
    val kotlinSerializationJson by lazy { "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serializationVersion}" }

    val list =
        listOf(coreKtx, platformKotlin, coroutinesCore, coroutinesCore, lifecycleRuntimeKtx, coroutinesAndroid, ktxViewModel)
}

object Compose {
    private val compose by lazy { "androidx.activity:activity-compose:${Versions.activityComposeVersion}" }
    private val platformCompose by lazy { "androidx.compose:compose-bom:${Versions.platformComposeBomVersion}" }
    private val composeUi by lazy { "androidx.compose.ui:ui" }
    private val composeUiGraphics by lazy { "androidx.compose.ui:ui-graphics" }
    private val composeUiPreview by lazy { "androidx.compose.ui:ui-tooling-preview" }
    private val material3 by lazy { "androidx.compose.material3:material3:${Versions.material3Version}" }
    private val foundation by lazy { "androidx.compose.foundation:foundation:${Versions.foundationVersion}" }
    private val navigation by lazy { "androidx.navigation:navigation-compose:${Versions.composeNavigationVersion}" }
    val composeViewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycleViewmodelComposeVersion}" }
    val material by lazy { "androidx.compose.material:material:${Versions.materialVersion}" }
    val materialNavigation by lazy { "androidx.compose.material:material-navigation:${Versions.materialNavigationVersion}" }
    val list = listOf(
        compose, platformCompose, composeUi, composeUiGraphics, composeUiPreview,
        material3, foundation, navigation, composeViewModel
    )
}

object Di {
    val hiltAndroid by lazy { "com.google.dagger:hilt-android:${Versions.hiltAndroidVersion}" }
    val hiltAndroidCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hiltAndroidCompilerVersion}" }
    val hiltNavigationCompose by lazy { "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationComposeVersion}" }
    val hiltCompiler by lazy { "androidx.hilt:hilt-compiler:${Versions.hiltCompilerVersion}" }
    val list = listOf(hiltAndroid, hiltAndroidCompiler, hiltNavigationCompose, hiltCompiler)
}
object Test {
    val junit by lazy { "junit:junit:${Versions.junitVersion}" }
    val junitTest by lazy { "androidx.test.ext:junit:${Versions.junitTestVersion}" }
    val espressoCore by lazy { "androidx.test.espresso:espresso-core:${Versions.espressoCoreVersion}" }
    val composeTest by lazy { "androidx.compose:compose-bom:${Versions.platformComposeBomTestVersion}" }
    val composeTestJunit4 by lazy { "androidx.compose.ui:ui-test-junit4" }
    val composeTestTooling by lazy { "androidx.compose.ui:ui-tooling" }
    val composeTestManifest by lazy { "androidx.compose.ui:ui-test-manifest" }
}
object Firebase {
    val platformFirebase by lazy { "com.google.firebase:firebase-bom:${Versions.platformFirebaseBomVersion}" }
    val firebaseAuth by lazy { "com.google.firebase:firebase-auth-ktx" }
    val crashlytics by lazy { "com.google.firebase:firebase-crashlytics-ktx" }
    val firestore by lazy { "com.google.firebase:firebase-firestore-ktx" }
    val storage by lazy { "com.google.firebase:firebase-storage-ktx" }
    val config by lazy { "com.google.firebase:firebase-config-ktx" }
}
object Room {
    val room by lazy { "androidx.room:room-ktx:${Versions.roomVersion}" }
    val roomRuntime by lazy { "androidx.room:room-runtime:${Versions.roomVersion}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.roomVersion}" }
}

object ThirdParty {
    val calendar by lazy { "com.kizitonwose.calendar:compose:${Versions.calendarVersion}" }
    val chart by lazy { "com.github.PhilJay:MPAndroidChart:${Versions.chartVersion}" }
    val youtubePlayer by lazy { "com.pierfrancescosoffritti.androidyoutubeplayer:chromecast-sender:${Versions.youtubePlayerVersion}" }
    val coil by lazy { "io.coil-kt:coil-compose:${Versions.coilVersion}" }
    val numberPicker by lazy { "com.chargemap.compose:numberpicker:${Versions.numberpickerVersion}" }
    val desugaring by lazy { "com.android.tools:desugar_jdk_libs:${Versions.desugaringVersion}" }
}

object Network {
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}" }
    val gsonConvertor by lazy { "com.squareup.retrofit2:converter-gson:${Versions.converterGsonVersion}" }
    val okHttp by lazy { "com.squareup.okhttp3:okhttp:${Versions.okhttpVersion}" }
    val okHttpInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpInterceptorVersion}" }
    val list = listOf(retrofit, gsonConvertor, okHttp, okHttpInterceptor)
}
object Google {
    val googleServicesAuth by lazy { "com.google.android.gms:play-services-auth:${Versions.googleServicesAuthVersion}" }
}

object Androidx {
    val splash by lazy { "androidx.core:core-splashscreen:${Versions.splashScreenVersion}" }
    val datastore by lazy { "androidx.datastore:datastore-preferences:${Versions.datastoreVersion}" }
}

object Player {
    val media3 by lazy { "androidx.media3:media3-ui:${Versions.media3Version}" }
    val media3_exoplayer by lazy { "androidx.media3:media3-exoplayer:${Versions.media3Version}" }
}
object AppUpdate {
    val inappUpdate by lazy { "com.google.android.play:app-update:${Versions.updateVersion}" }
    val inappUpdateKtx by lazy { "com.google.android.play:app-update:${Versions.updateVersion}" }
}
