import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Created by bedirhansaricayir on 27.01.2025
 */

class RoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(BuildPlugins.KSP_PLUGIN)
            }
            dependencies {
                room()
            }
        }
    }
}