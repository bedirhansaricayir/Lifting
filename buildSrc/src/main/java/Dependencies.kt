object Dependencies {

    //Core
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtxVersion}" }
    val platformKotlin by lazy { "org.jetbrains.kotlin:kotlin-bom:${Versions.platformKotlinBomVersion}" }
    val lifecycleRuntimeKtx by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtxVersion}" }
    val compose by lazy { "androidx.activity:activity-compose:${Versions.activityComposeVersion}" }
    val platformCompose by lazy { "androidx.compose:compose-bom:${Versions.platformComposeBomVersion}" }
    val composeUi by lazy { "androidx.compose.ui:ui" }
    val composeUiGraphics by lazy { "androidx.compose.ui:ui-graphics" }
    val composeUiPreview by lazy { "androidx.compose.ui:ui-tooling-preview" }
    val material3 by lazy { "androidx.compose.material3:material3:${Versions.material3Version}" }
    val foundation by lazy { "androidx.compose.foundation:foundation:${Versions.foundationVersion}" }


    //Test
    val junit by lazy { "junit:junit:${Versions.junitVersion}" }
    val junitTest by lazy { "androidx.test.ext:junit:${Versions.junitTestVersion}" }
    val espressoCore by lazy { "androidx.test.espresso:espresso-core:${Versions.espressoCoreVersion}" }
    val composeTest by lazy { "androidx.compose:compose-bom:${Versions.platformComposeBomTestVersion}" }
    val composeTestJunit4 by lazy { "androidx.compose.ui:ui-test-junit4" }
    val composeTestTooling by lazy { "androidx.compose.ui:ui-tooling" }
    val composeTestManifest by lazy { "androidx.compose.ui:ui-test-manifest" }

    //Viewmodel
    val composeViewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycleViewmodelComposeVersion}" }
    val ktxViewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewmodelKtxVersion}" }

    //Coroutines
    val coroutinesCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCoreVersion}" }
    val coroutinesAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroidVersion}" }

    //Retrofit
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}" }
    val gsonConvertor by lazy { "com.squareup.retrofit2:converter-gson:${Versions.converterGsonVersion}" }
    val okHttp by lazy { "com.squareup.okhttp3:okhttp:${Versions.okhttpVersion}" }
    val okHttpInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpInterceptorVersion}" }

    //Navigation
    val navigation by lazy { "androidx.navigation:navigation-compose:${Versions.composeNavigationVersion}" }

    //Splash API
    val splash by lazy { "androidx.core:core-splashscreen:${Versions.splashScreenVersion}" }

    //Dagger - Hilt
    val hiltAndroid by lazy { "com.google.dagger:hilt-android:${Versions.hiltAndroidVersion}" }
    val hiltAndroidCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hiltAndroidCompilerVersion}" }
    val hiltNavigationCompose by lazy { "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationComposeVersion}" }
    val hiltCompiler by lazy { "androidx.hilt:hilt-compiler:${Versions.hiltCompilerVersion}" }

    //Datastore
    val datastore by lazy { "androidx.datastore:datastore-preferences:${Versions.datastoreVersion}" }

    //Numberpicker
    val numberPicker by lazy { "com.chargemap.compose:numberpicker:${Versions.numberpickerVersion}" }

    //Firebase
    val platformFirebase by lazy { "com.google.firebase:firebase-bom:${Versions.platformFirebaseBomVersion}" }
    val firebaseAuth by lazy { "com.google.firebase:firebase-auth-ktx" }
    val crashlytics by lazy { "com.google.firebase:firebase-crashlytics-ktx" }
    val firestore by lazy { "com.google.firebase:firebase-firestore-ktx" }
    val storage by lazy { "com.google.firebase:firebase-storage-ktx" }
    val config by lazy { "com.google.firebase:firebase-config-ktx" }

    //GoogleAuth
    val googleServicesAuth by lazy { "com.google.android.gms:play-services-auth:${Versions.googleServicesAuthVersion}" }

    //Coil
    val coil by lazy { "io.coil-kt:coil-compose:${Versions.coilVersion}" }

    //Player
    val youtubePlayer by lazy { "com.pierfrancescosoffritti.androidyoutubeplayer:chromecast-sender:${Versions.youtubePlayerVersion}" }

    //Chart
    val chart by lazy { "com.github.PhilJay:MPAndroidChart:${Versions.chartVersion}" }

    //Room
    val room by lazy { "androidx.room:room-ktx:${Versions.roomVersion}" }
    val roomRuntime by lazy { "androidx.room:room-runtime:${Versions.roomVersion}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.roomVersion}" }
    
    //Calendar
    val calendar by lazy { "com.kizitonwose.calendar:compose:${Versions.calendarVersion}" }

    //Desugaring
    val desugaring by lazy { "com.android.tools:desugar_jdk_libs:${Versions.desugaringVersion}" }

    //Media3
    val media3 by lazy { "androidx.media3:media3-ui:${Versions.media3Version}" }
    val media3_exoplayer by lazy { "androidx.media3:media3-exoplayer:${Versions.media3Version}" }

    val inappUpdate by lazy { "com.google.android.play:app-update:${Versions.updateVersion}" }
    val inappUpdateKtx by lazy { "com.google.android.play:app-update:${Versions.updateVersion}" }
}