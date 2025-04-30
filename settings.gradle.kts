pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") } //Add this line in your settings.gradle

    }
}
rootProject.name = "Lifting"
include (":app")
include(":core:network")
include(":core:common")
include(":core:datastore")
include(":core:base")
include(":core:navigation")
include(":core:designsystem")
include(":core:data")
include(":core:database")
include(":core:model")
include(":core:ui")
include(":feature:exercises")
include(":feature:create-exercise")
include(":feature:exercises-category")
include(":feature:exercises-muscle")
include(":feature:workout")
include(":feature:workout-edit")
include(":feature:workout-template-preview")
include(":feature:exercise-detail")
include(":feature:history")
include(":feature:calendar")
include(":feature:workout-detail")