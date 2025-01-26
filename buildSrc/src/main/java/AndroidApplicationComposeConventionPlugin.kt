import BuildPlugins.COMPOSE_PLUGIN
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Created by bedirhansaricayir on 26.01.2025
 */

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(COMPOSE_PLUGIN)
            }
            val extension = extensions.getByType<ApplicationExtension>()

            with(extension) {
                buildFeatures {
                    compose = true
                }
            }

            dependencies {
                compose()
            }
        }
    }
}

