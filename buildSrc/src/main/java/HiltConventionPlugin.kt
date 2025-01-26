import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Created by bedirhansaricayir on 14.12.2024
 */

class HiltConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(BuildPlugins.DAGGER_HILT)
                apply(BuildPlugins.KSP_PLUGIN)
                dependencies {
                    hilt()
                }
            }
        }
    }
}