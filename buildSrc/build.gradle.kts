plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.0")
    implementation("com.android.tools.build:gradle:8.4.0")
    implementation("com.squareup:javapoet:1.13.0")
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "lifting.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "lifting.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "lifting.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "lifting.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "lifting.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("hilt") {
            id = "lifting.hilt"
            implementationClass = "HiltConventionPlugin"
        }
        register("room") {
            id = "lifting.room"
            implementationClass = "RoomConventionPlugin"
        }
        register("network") {
            id = "lifting.network"
            implementationClass = "NetworkConventionPlugin"
        }
        register("jvmLibrary") {
            id = "lifting.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}