package com.lifting.app.feature_calculators.presentation.tools_detail.bmi

import android.content.Context

sealed class BMIScreenEvent {
    object OnWeightValueClicked : BMIScreenEvent()
    object OnHeightValueClicked : BMIScreenEvent()
    object OnWeightTextClicked : BMIScreenEvent()
    object OnHeightTextClicked : BMIScreenEvent()
    object OnGoButtonClicked : BMIScreenEvent()
    data class OnNumberClicked(val number: String) : BMIScreenEvent()
    object OnAllClearButtonClicked : BMIScreenEvent()
    object OnDeleteButtonClicked : BMIScreenEvent()
    data class OnSheetItemClicked(val sheetItem: String) : BMIScreenEvent()
    object OnRefreshIconClicked : BMIScreenEvent()
    object OnViewedErrorMessage : BMIScreenEvent()
}