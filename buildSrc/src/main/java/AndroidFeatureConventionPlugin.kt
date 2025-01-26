import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Created by bedirhansaricayir on 14.12.2024
 */

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("lifting.android.library")
                apply("lifting.hilt")
            }
            dependencies {
                featureModules()
                kotlin()
            }
        }
    }
}