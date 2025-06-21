package com.lifting.app.feature.settings

import android.content.ContentResolver
import android.net.Uri
import com.lifting.app.core.base.viewmodel.Event
import com.lifting.app.core.model.AppLanguage
import com.lifting.app.core.model.AppTheme
import com.lifting.app.core.model.DistanceUnit
import com.lifting.app.core.model.WeightUnit

/**
 * Created by bedirhansaricayir on 16.05.2025
 */
sealed interface SettingsUIEvent : Event {
    data class OnAppLanguageSelected(val appLanguage: AppLanguage) : SettingsUIEvent
    data class OnAppThemeSelected(val appTheme: AppTheme) : SettingsUIEvent
    data class OnWeightUnitSelected(val weightUnit: WeightUnit) : SettingsUIEvent
    data class OnDistanceUnitSelected(val distanceUnit: DistanceUnit) : SettingsUIEvent
    data class OnBackupDataClicked(val uri: Uri, val contentResolver: ContentResolver) : SettingsUIEvent
    data class OnRestoreDataClicked(val uri: Uri, val contentResolver: ContentResolver) : SettingsUIEvent
    data object OnWriteReviewClicked : SettingsUIEvent
    data object OnSuggestionAndBugClicked : SettingsUIEvent
}