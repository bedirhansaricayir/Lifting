package extensions

import AppConfig
import com.android.build.gradle.BaseExtension
import desugaring
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Created by bedirhansaricayir on 20.12.2024
 */

internal fun Project.configureAndroid() {
    android {
        namespace = AppConfig.APPLICATION_ID
        compileSdkVersion(AppConfig.COMPILE_SDK)

        defaultConfig {
            applicationId = AppConfig.APPLICATION_ID
            minSdk = AppConfig.MIN_SDK
            targetSdk = AppConfig.TARGET_SDK
            versionCode = AppConfig.versionCode
            versionName = AppConfig.versionName

            testInstrumentationRunner = AppConfig.testInstrumentationRunner
        }

        compileOptions {
            isCoreLibraryDesugaringEnabled = true
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

    }

    dependencies {
        desugaring()
    }
}

private fun Project.android(action: BaseExtension.() -> Unit) =
    extensions.configure<BaseExtension>(action)
