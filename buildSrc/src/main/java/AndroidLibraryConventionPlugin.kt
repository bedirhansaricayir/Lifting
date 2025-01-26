import com.android.build.api.dsl.LibraryExtension
import extensions.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Created by bedirhansaricayir on 14.12.2024
 */

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(BuildPlugins.ANDROID_LIBRARY_PLUGIN)
                apply(BuildPlugins.KOTLIN_ANDROID_PLUGIN)
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.testInstrumentationRunner = AppConfig.testInstrumentationRunner
            }
        }
    }
}