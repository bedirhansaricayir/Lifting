package com.lifting.app.core.navigation.screens

import kotlinx.serialization.Serializable

@Serializable
sealed class NavBarScreen(
    val startScreen: LiftingScreen
)  {
    @Serializable data object Dashboard : NavBarScreen(LiftingScreen.DashboardTab)
    @Serializable data object History : NavBarScreen(LiftingScreen.HistoryTab)
    @Serializable data object Workout : NavBarScreen(LiftingScreen.WorkoutTab)
    @Serializable data object Exercises : NavBarScreen(LiftingScreen.ExercisesTab)
    @Serializable data object Settings : NavBarScreen(LiftingScreen.SettingsTab)
}

@Serializable
sealed class LiftingScreen(

) {
    //<!-- region NavBarRoot -->
    @Serializable data object DashboardTab : LiftingScreen()
    @Serializable data object HistoryTab : LiftingScreen()
    @Serializable data object WorkoutTab : LiftingScreen()
    @Serializable data object ExercisesTab : LiftingScreen()
    @Serializable data object SettingsTab : LiftingScreen()
    //<!-- endregion -->

    //<!-- region Sub-Dashboard -->
    @Serializable data object Dashboard : LiftingScreen()
    //<!-- endregion -->

    //<!-- region Sub-History -->
    @Serializable data object History : LiftingScreen()
    @Serializable data class Calendar(val selectedDateKey: String) : LiftingScreen()
    @Serializable data class Session(val workoutIdKey: String) : LiftingScreen()
    //<!-- endregion -->

    //<!-- region Sub-Workout -->
    @Serializable data object Workout : LiftingScreen()
    @Serializable data class WorkoutEdit(val workoutIdKey: String, val isTemplateKey: Boolean) : LiftingScreen()
    @Serializable data class WorkoutTemplatePreview(val templateIdKey: String) : LiftingScreen()
    //<!-- endregion -->

    //<!-- region Sub-Exercises -->
    @Serializable data object Exercises : LiftingScreen()
    @Serializable data class ExerciseDetail(val exerciseIdKey: String) : LiftingScreen()
    //<!-- endregion -->

    //<!-- region Sub-Workout -->
    @Serializable data object Settings : LiftingScreen()
    @Serializable data object Measure : LiftingScreen()
    //<!-- endregion -->


    //<<!-- region BottomSheet -->
    @Serializable data class CreateExercisesBottomSheet(val route: String = this.toString()) : LiftingScreen()
    @Serializable data class ExercisesCategoryBottomSheet(val route: String = this.toString()) : LiftingScreen()
    @Serializable data class ExercisesMuscleBottomSheet(val route: String = this.toString()) : LiftingScreen()
    @Serializable data class ExercisesBottomSheet(val route: String = this.toString()) : LiftingScreen()
    //<!-- endregion -->
}