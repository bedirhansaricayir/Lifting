package com.lifting.app.core.navigation.screens

import android.annotation.SuppressLint
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

@SuppressLint("UnsafeOptInUsageError")
@Serializable
sealed class LiftingScreen() {
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
    @Serializable data object Calendar : LiftingScreen()
    @Serializable data class WorkoutDetail(val workoutIdKey: String) : LiftingScreen()
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
    @Serializable data class BarbellSelectorBottomSheet(val route: String = this.toString()) : LiftingScreen()
    @Serializable data class SupersetSelectorBottomSheet(val route: String = this.toString()) : LiftingScreen()
    @Serializable data class RestTimerBottomSheet(val route: String = this.toString()) : LiftingScreen()
    //<!-- endregion -->

    companion object {
        const val SELECTED_EXERCISE_CATEGORY = "SELECTED_EXERCISE_CATEGORY"
        const val SELECTED_EXERCISE_MUSCLE = "SELECTED_EXERCISE_MUSCLE"
        const val RESULT_EXERCISES_SCREEN_EXERCISE_ID = "result_exercises_screen_exercise_id"
        const val WORKOUT_ID_KEY = "workout_id"
        const val TEMPLATE_ID_KEY = "template_id"
        const val SELECTED_BARBELL_JUNCTION_RESULT = "selected_barbell_junction_result"
        const val RESULT_SUPERSET_SELECTOR_SUPERSET_ID_KEY = "result_superset_selector_superset_id"

    }
}