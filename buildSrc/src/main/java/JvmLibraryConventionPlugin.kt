import extensions.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by bedirhansaricayir on 14.12.2024
 */

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(BuildPlugins.KOTLIN_JVM_PLUGIN)
            }
            configureKotlinJvm()
        }
    }
}