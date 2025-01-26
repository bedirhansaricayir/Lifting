package extensions

import com.android.build.api.dsl.CommonExtension
import compose
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Created by bedirhansaricayir on 14.12.2024
 */

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        dependencies {
            compose()
        }

//        testOptions {
//            unitTests {
//                // For Robolectric
//                isIncludeAndroidResources = true
//            }
//        }
    }
}