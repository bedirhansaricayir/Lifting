import extensions.coreLibraryDesugaring
import extensions.implementation
import extensions.ksp
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

fun DependencyHandler.featureExercises() = implementation(project(":feature:exercises"))
fun DependencyHandler.featureCreateExercise() = implementation(project(":feature:create-exercise"))
fun DependencyHandler.featureExercisesCategory() = implementation(project(":feature:exercises-category"))
fun DependencyHandler.featureExercisesMuscle() = implementation(project(":feature:exercises-muscle"))
fun DependencyHandler.featureWorkout() = implementation(project(":feature:workout"))
fun DependencyHandler.featureWorkoutEdit() = implementation(project(":feature:workout-edit"))
fun DependencyHandler.featureWorkoutTemplatePreview() = implementation(project(":feature:workout-template-preview"))
internal fun DependencyHandler.featureModules() = listOf(coreBase(),coreNavigation(),coreUI(),coreDesignSystem(),coreData(),coreModel(),coreCommon())
private fun DependencyHandler.coreBase() = implementation(project(":core:base"))
fun DependencyHandler.coreCommon() = implementation(project(":core:common"))
fun DependencyHandler.coreDatastore() = implementation(project(":core:datastore"))
fun DependencyHandler.coreModel() = implementation(project(":core:model"))
fun DependencyHandler.coreNavigation() = implementation(project(":core:navigation"))
private fun DependencyHandler.coreUI() = implementation(project(":core:ui"))
private fun DependencyHandler.coreData() = implementation(project(":core:data"))
fun DependencyHandler.coreDatabase() = implementation(project(":core:database"))
fun DependencyHandler.coreDesignSystem() = implementation(project(":core:designsystem"))
internal fun DependencyHandler.compose() = Compose.list.forEach(::implementation)
internal fun DependencyHandler.kotlin() = Kotlin.list.forEach(::implementation)
internal fun DependencyHandler.network() = Network.list.forEach(::implementation)
internal fun DependencyHandler.desugaring() = coreLibraryDesugaring(ThirdParty.desugaring)
internal fun DependencyHandler.hilt() {
    with(Di) {
        implementation(hiltAndroid)
        implementation(hiltNavigationCompose)
        ksp(hiltCompiler)
        ksp(hiltAndroidCompiler)
    }
}
internal fun DependencyHandler.room() {
    with(Room) {
        implementation(room)
        implementation(roomRuntime)
        ksp(roomCompiler)
    }
}
object Kotlin {
    private const val coreKtxVersion = "1.15.0"
    private const val platformKotlinBomVersion = "1.8.0"
    private const val lifecycleRuntimeKtxVersion = "2.7.0"
    private const val coroutinesCoreVersion = "1.7.1"
    private const val coroutinesAndroidVersion = "1.7.1"
    private const val lifecycleViewmodelKtxVersion = "2.8.0"
    private const val serializationVersion = "1.6.0"

    private val coreKtx by lazy { "androidx.core:core-ktx:${coreKtxVersion}" }
    private val platformKotlin by lazy { "org.jetbrains.kotlin:kotlin-bom:${platformKotlinBomVersion}" }
    private val coroutinesCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutinesCoreVersion}" }
    private val coroutinesAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${coroutinesAndroidVersion}" }
    private val lifecycleRuntimeKtx by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${lifecycleRuntimeKtxVersion}" }
    val ktxViewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${lifecycleViewmodelKtxVersion}" }
    val kotlinSerializationJson by lazy { "org.jetbrains.kotlinx:kotlinx-serialization-json:${serializationVersion}" }
    val kotlinStdLib by lazy { "org.jetbrains.kotlin:kotlin-stdlib:2.0.0" }

    val list =
        listOf(coreKtx, platformKotlin, coroutinesCore, lifecycleRuntimeKtx, coroutinesAndroid, ktxViewModel, kotlinStdLib)
}

object Compose {
    private const val activityComposeVersion = "1.9.0"
    private const val platformComposeBomVersion = "2024.05.00"
    private const val lifecycleViewmodelComposeVersion = "2.8.0"
    private const val material3Version = "1.2.1"
    private const val foundationVersion = "1.7.0-alpha07"
    private const val materialVersion = "1.7.0-beta04"
    private const val composeNavigationVersion = "2.8.0-alpha08"
    private const val materialNavigationVersion = "1.7.0-beta01"

    private val compose by lazy { "androidx.activity:activity-compose:${activityComposeVersion}" }
    private val platformCompose by lazy { "androidx.compose:compose-bom:${platformComposeBomVersion}" }
    private val composeUi by lazy { "androidx.compose.ui:ui" }
    private val composeUiGraphics by lazy { "androidx.compose.ui:ui-graphics" }
    private val composeUiPreview by lazy { "androidx.compose.ui:ui-tooling-preview" }
    private val material3 by lazy { "androidx.compose.material3:material3:${material3Version}" }
    private val foundation by lazy { "androidx.compose.foundation:foundation:${foundationVersion}" }
    private val navigation by lazy { "androidx.navigation:navigation-compose:${composeNavigationVersion}" }
    private val composeViewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-compose:${lifecycleViewmodelComposeVersion}" }
    val material by lazy { "androidx.compose.material:material:${materialVersion}" }
    val materialNavigation by lazy { "androidx.compose.material:material-navigation:${materialNavigationVersion}" }
    val list = listOf(
        compose, platformCompose, composeUi, composeUiGraphics, composeUiPreview,
        material3, foundation, navigation, composeViewModel
    )
}

private object Di {
    private const val hiltAndroidVersion = "2.48"
    private const val hiltAndroidCompilerVersion = "2.48"
    private const val hiltNavigationComposeVersion = "1.2.0"
    private const val hiltCompilerVersion = "1.2.0"

    val hiltAndroid by lazy { "com.google.dagger:hilt-android:${hiltAndroidVersion}" }
    val hiltAndroidCompiler by lazy { "com.google.dagger:hilt-android-compiler:${hiltAndroidCompilerVersion}" }
    val hiltNavigationCompose by lazy { "androidx.hilt:hilt-navigation-compose:${hiltNavigationComposeVersion}" }
    val hiltCompiler by lazy { "androidx.hilt:hilt-compiler:${hiltCompilerVersion}" }
    val list = listOf(hiltAndroid, hiltAndroidCompiler, hiltNavigationCompose, hiltCompiler)
}
object Test {
    private const val junitVersion = "4.13.2"
    private const val junitTestVersion = "1.1.5"
    private const val espressoCoreVersion = "3.5.1"
    private const val platformComposeBomTestVersion = "2022.10.00"

    val junit by lazy { "junit:junit:${junitVersion}" }
    val junitTest by lazy { "androidx.test.ext:junit:${junitTestVersion}" }
    val espressoCore by lazy { "androidx.test.espresso:espresso-core:${espressoCoreVersion}" }
    val composeTest by lazy { "androidx.compose:compose-bom:${platformComposeBomTestVersion}" }
    val composeTestJunit4 by lazy { "androidx.compose.ui:ui-test-junit4" }
    val composeTestTooling by lazy { "androidx.compose.ui:ui-tooling" }
    val composeTestManifest by lazy { "androidx.compose.ui:ui-test-manifest" }
}
object Firebase {
    private const val platformFirebaseBomVersion = "32.1.1"

    val platformFirebase by lazy { "com.google.firebase:firebase-bom:${platformFirebaseBomVersion}" }
    val firebaseAuth by lazy { "com.google.firebase:firebase-auth-ktx" }
    val crashlytics by lazy { "com.google.firebase:firebase-crashlytics-ktx" }
    val firestore by lazy { "com.google.firebase:firebase-firestore-ktx" }
    val storage by lazy { "com.google.firebase:firebase-storage-ktx" }
    val config by lazy { "com.google.firebase:firebase-config-ktx" }
}
internal object Room {
    private const val roomVersion = "2.6.1"

    val room by lazy { "androidx.room:room-ktx:${roomVersion}" }
    val roomRuntime by lazy { "androidx.room:room-runtime:${roomVersion}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${roomVersion}" }
}

object ThirdParty {
    private const val coilVersion = "2.7.0"
    private const val desugaringVersion = "2.0.3"
    private const val collapsingToolbarVersion = "2.3.5"

    val coil by lazy { "io.coil-kt:coil-compose:${coilVersion}" }
    val desugaring by lazy { "com.android.tools:desugar_jdk_libs:${desugaringVersion}" }
    val collapsingToolbar by lazy { "me.onebone:toolbar-compose:${collapsingToolbarVersion}" }
}

private object Network {
    private const val retrofitVersion = "2.9.0"
    private const val converterGsonVersion = "2.9.0"
    private const val okhttpVersion = "5.0.0-alpha.2"
    private const val okhttpInterceptorVersion = "5.0.0-alpha.2"

    private val retrofit by lazy { "com.squareup.retrofit2:retrofit:${retrofitVersion}" }
    private val gsonConvertor by lazy { "com.squareup.retrofit2:converter-gson:${converterGsonVersion}" }
    private val okHttp by lazy { "com.squareup.okhttp3:okhttp:${okhttpVersion}" }
    private val okHttpInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${okhttpInterceptorVersion}" }
    val list = listOf(retrofit, gsonConvertor, okHttp, okHttpInterceptor)
}
object Androidx {
    private const val splashScreenVersion = "1.0.1"
    private const val datastoreVersion = "1.0.0"

    val splash by lazy { "androidx.core:core-splashscreen:${splashScreenVersion}" }
    val datastore by lazy { "androidx.datastore:datastore-preferences:${datastoreVersion}" }
}