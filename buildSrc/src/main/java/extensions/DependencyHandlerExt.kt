package extensions

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * Created by bedirhansaricayir on 13.12.2024
 */

internal fun DependencyHandler.implementation(dependency: String) {
    add("implementation", dependency)
}

internal fun DependencyHandler.implementation(dependency: Dependency) {
    add("implementation", dependency)
}

internal fun DependencyHandler.test(dependency: String) {
    add("test", dependency)
}

internal fun DependencyHandler.androidTest(dependency: String) {
    add("androidTest", dependency)
}

internal fun DependencyHandler.debugImplementation(dependency: String) {
    add("debugImplementation", dependency)
}

internal fun DependencyHandler.ksp(dependency: String) {
    add("ksp", dependency)
}

internal fun DependencyHandler.coreLibraryDesugaring(dependency: String) {
    add("coreLibraryDesugaring",dependency)
}