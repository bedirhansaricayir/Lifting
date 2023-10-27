package com.lifting.app.feature_calculators.presentation.tools_detail.bmr

import androidx.annotation.StringRes
import com.lifting.app.R

data class BMRScreenState(
    val gender: Gender = Gender.MEN,
    val activityLevel: ActivityLevel = ActivityLevel.SEDENTARY,
    val bmr: Double = 0.0,
    val dailyCalories: Double = 0.0,
    val measurements: BodyMeasurements = BodyMeasurements()
)

data class BodyMeasurements(
    val weight: Int = 75,
    val height: Int = 175,
    val age: Int = 20
)

enum class Gender(@StringRes val gender: Int) {
    MEN(R.string.gender_male_label),
    WOMEN(R.string.gender_female_label)
}

enum class ActivityLevel(@StringRes val activityLevel: Int,@StringRes val activityLevelDesc: Int) {
    SEDENTARY(R.string.activity_level_sedentary,R.string.activity_level_sedentary_desc),
    LOW(R.string.activity_level_low,R.string.activity_level_low_desc),
    MEDIUM(R.string.activity_level_medium,R.string.activity_level_medium_desc),
    HIGH(R.string.activity_level_high,R.string.activity_level_high_desc)
}

