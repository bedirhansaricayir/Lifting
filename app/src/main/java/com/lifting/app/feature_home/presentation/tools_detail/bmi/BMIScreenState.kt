package com.lifting.app.feature_home.presentation.tools_detail.bmi

import androidx.annotation.StringRes
import com.lifting.app.R

data class BMIScreenState(
    val weightValue: String = "60",
    val heightValue: String = "170",
    val weightValueStage: WeightValueStage = WeightValueStage.ACTIVE,
    val heightValueStage: HeightValueStage = HeightValueStage.INACTIVE,
    val shouldBMICardShow: Boolean = false,
    val sheetTitle: SheetTitleStage = SheetTitleStage.WEIGHT,
    val sheetItemsList: List<String> = emptyList(),
    val weightUnit: String = "Kilogram",
    val heightUnit: String = "Centimeters",
    val bmi: Double = 0.0,
    val bmiStage: BMIRangeState = BMIRangeState.NORMAL,
    val error: Boolean = false,
)
enum class SheetTitleStage {
    WEIGHT,HEIGHT
}

enum class BMIRangeState(@StringRes val text: Int) {
    UNDERWEIGHT(R.string.underweight_label),
    NORMAL(R.string.normal_label),
    OVERWEIGHT(R.string.overweight_label),
    NONE(R.string.none_label)
}
enum class WeightValueStage {
    INACTIVE,
    ACTIVE,
    RUNNING
}

enum class HeightValueStage {
    INACTIVE,
    ACTIVE,
    RUNNING
}