package com.lifting.app.feature.settings

import android.app.Application
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.datastore.PreferencesStorage
import com.lifting.app.core.model.AppLanguage
import com.lifting.app.core.model.AppTheme
import com.lifting.app.core.model.DistanceUnit
import com.lifting.app.core.model.WeightUnit
import com.lifting.app.core.ui.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject
import kotlin.system.exitProcess

/**
 * Created by bedirhansaricayir on 16.05.2025
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesStorage: PreferencesStorage,
    private val application: Application
) : BaseViewModel<SettingsUIState, SettingsUIEvent, SettingsUIEffect>() {

    override fun setInitialState(): SettingsUIState = SettingsUIState()

    override fun handleEvents(event: SettingsUIEvent) {
        when (event) {
            is SettingsUIEvent.OnAppLanguageSelected -> updateAppLanguage(event.appLanguage)
            is SettingsUIEvent.OnAppThemeSelected -> updateAppTheme(event.appTheme)
            is SettingsUIEvent.OnWeightUnitSelected -> updateWeightUnit(event.weightUnit)
            is SettingsUIEvent.OnDistanceUnitSelected -> updateDistanceUnit(event.distanceUnit)
            is SettingsUIEvent.OnBackupDataClicked -> takeBackup(event.uri, event.contentResolver)
            is SettingsUIEvent.OnRestoreDataClicked -> restoreBackup(
                event.uri,
                event.contentResolver
            )

            SettingsUIEvent.OnWriteReviewClicked -> navigateToAppStore()
            SettingsUIEvent.OnSuggestionAndBugClicked -> navigateToMail()
        }
    }

    init {
        initUIState()
    }


    private fun initUIState() {
        combine(
            preferencesStorage.appTheme,
            preferencesStorage.weightUnit,
            preferencesStorage.distanceUnit,
            preferencesStorage.appLanguage
        ) { appTheme, weightUnit, distanceUnit, appLanguage ->
            updateState {
                it.copy(
                    listUi = mapOf(
                        com.lifting.app.core.ui.R.string.settings_screen_preferences_title to listOf(
                            appLanguage,
                            appTheme,
                            weightUnit,
                            distanceUnit
                        ),
                        com.lifting.app.core.ui.R.string.settings_screen_your_data_title to listOf(
                            SettingsItem.BACKUP_DATA,
                            SettingsItem.RESTORE_DATA
                        ),
                        com.lifting.app.core.ui.R.string.settings_screen_feedback_title to listOf(
                            SettingsItem.WRITE_REVIEW,
                            SettingsItem.SUGGESTION_BUG
                        )
                    ),
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun updateAppLanguage(appLanguage: AppLanguage) {
        viewModelScope.launch {
            preferencesStorage.setAppLanguage(appLanguage)
        }
    }

    private fun updateAppTheme(appTheme: AppTheme) {
        viewModelScope.launch {
            preferencesStorage.setAppTheme(appTheme)
        }
    }

    private fun updateWeightUnit(weightUnit: WeightUnit) {
        viewModelScope.launch {
            preferencesStorage.setWeightUnit(weightUnit)
        }
    }

    private fun updateDistanceUnit(distanceUnit: DistanceUnit) {
        viewModelScope.launch {
            preferencesStorage.setDistanceUnit(distanceUnit)
        }
    }

    private fun takeBackup(uri: Uri, contentResolver: ContentResolver) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                contentResolver.openOutputStream(uri)?.use { output ->
                    FileInputStream(getDatabasePath()).use { input ->
                        input.copyTo(output)
                    }
                }
            }.onSuccess {
                showNotification(UiText.StringResource(com.lifting.app.core.ui.R.string.backup_file_created_successfully))
            }.getOrElse { throwable ->
                Log.d("Backup Failed", throwable.message.toString())
            }
        }
    }

    private fun restoreBackup(uri: Uri, contentResolver: ContentResolver) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                contentResolver.openInputStream(uri)?.use { input ->
                    FileOutputStream(getDatabasePath()).use { output ->
                        input.copyTo(output)
                    }
                }
            }.onSuccess {
                restartApp()
            }.getOrElse { throwable ->
                Log.d("Restore Failed", throwable.message.toString())
            }
        }
    }

    private fun getDatabasePath(): String {
        return application.getDatabasePath(DATABASE_NAME).absolutePath
    }

    private fun restartApp() {
        application.packageManager.getLaunchIntentForPackage(application.packageName).apply {
            this?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }.also { intent ->
            application.startActivity(intent)
            exitProcess(0)
        }
    }

    private fun navigateToAppStore() = setEffect(SettingsUIEffect.NavigateToAppStore)

    private fun navigateToMail() = setEffect(SettingsUIEffect.NavigateToMail)

    private fun showNotification(message: UiText) =
        setEffect(SettingsUIEffect.ShowNotification(message))

}

private const val DATABASE_NAME = "Lifting.db"