import BuildPlugins.ANDROID_APPLICATION_PLUGIN
import BuildPlugins.KOTLIN_ANDROID_PLUGIN
import extensions.configureAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by bedirhansaricayir on 20.12.2024
 */

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(ANDROID_APPLICATION_PLUGIN)
                apply(KOTLIN_ANDROID_PLUGIN)
            }
            configureAndroid()
        }
    }
}