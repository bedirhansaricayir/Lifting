package com.lifting.app.feature.settings

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lifting.app.core.common.utils.generateUUID
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.AppLanguage
import com.lifting.app.core.model.AppTheme
import com.lifting.app.core.model.DistanceUnit
import com.lifting.app.core.model.Selectable
import com.lifting.app.core.model.WeightUnit
import com.lifting.app.core.ui.CollapsingToolBarScaffold
import com.lifting.app.core.ui.common.AppLocale
import com.lifting.app.core.ui.common.UiText
import com.lifting.app.core.ui.top_bar.LiftingTopBar
import com.lifting.app.feature.settings.components.ExpandableSettingsItem
import com.lifting.app.feature.settings.components.SettingsItem
import com.lifting.app.feature.settings.components.SettingsSectionHeader
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

/**
 * Created by bedirhansaricayir on 16.05.2025
 */
@Composable
internal fun SettingsScreen(
    state: SettingsUIState,
    onEvent: (SettingsUIEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsScreenContent(
        state = state,
        onEvent = onEvent,
        modifier = modifier
    )
}

@Composable
private fun SettingsScreenContent(
    state: SettingsUIState,
    onEvent: (SettingsUIEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val scaffoldState = rememberCollapsingToolbarScaffoldState()
    val context = LocalContext.current

    val backupLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument(BACKUP_MIME_TYPE),
        onResult = { uri ->
            uri?.let {
                onEvent(SettingsUIEvent.OnBackupDataClicked(it, context.contentResolver))
            }
        }
    )

    val restoreLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                onEvent(SettingsUIEvent.OnRestoreDataClicked(it, context.contentResolver))
            }
        }
    )

    CollapsingToolBarScaffold(
        modifier = modifier.background(LiftingTheme.colors.background),
        state = scaffoldState,
        toolbar = {
            LiftingTopBar(
                title = stringResource(com.lifting.app.core.ui.R.string.top_bar_title_settings),
                toolbarState = scaffoldState.toolbarState,
                toolbarScope = this@CollapsingToolBarScaffold
            )
        },
        body = {
            val getPrefStringBySettingsItem: ((Any) -> Int) = { settingsItem: Any ->
                when (settingsItem) {
                    is AppTheme -> {
                        when (settingsItem) {
                            AppTheme.Light -> com.lifting.app.core.ui.R.string.settings_screen_app_theme_light_description
                            AppTheme.Dark -> com.lifting.app.core.ui.R.string.settings_screen_app_theme_dark_description
                            AppTheme.System -> com.lifting.app.core.ui.R.string.settings_screen_app_theme_system_description
                        }
                    }

                    is WeightUnit -> {
                        when (settingsItem) {
                            WeightUnit.Kg -> com.lifting.app.core.ui.R.string.weight_unit_description_metric_kg
                            WeightUnit.Lbs -> com.lifting.app.core.ui.R.string.weight_unit_description_imperial_lbs
                        }
                    }

                    is DistanceUnit -> {
                        when (settingsItem) {
                            DistanceUnit.Km -> com.lifting.app.core.ui.R.string.distance_unit_description_metric_m_km
                            DistanceUnit.Miles -> com.lifting.app.core.ui.R.string.distance_unit_description_imperial_ft_miles
                        }
                    }

                    is AppLanguage -> {
                        when (settingsItem) {
                            AppLanguage.English -> com.lifting.app.core.ui.R.string.app_language_description_english
                            AppLanguage.Turkish -> com.lifting.app.core.ui.R.string.app_language_description_turkish
                        }
                    }
                    else -> 0
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = LiftingTheme.dimensions.large),
            ) {
                state.listUi.entries.forEach { entries ->
                    item {
                        SettingsSectionHeader(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = stringResource(entries.key)
                        )
                    }

                    itemsIndexed(
                        items = entries.value,
                        key = { index, item ->
                            when (item) {
                                is AppTheme -> item.name
                                is WeightUnit -> item.name
                                is DistanceUnit -> item.name
                                is AppLanguage -> item.name
                                else -> generateUUID()
                            }
                        }
                    ) { index, item ->
                        when (item) {
                            is AppTheme -> {
                                ExpandableSettingsItem(
                                    shape = LiftingTheme.shapes.listShapes(entries.value.size,index),
                                    title = stringResource(com.lifting.app.core.ui.R.string.settings_screen_app_theme_title),
                                    icon = LiftingTheme.icons.palette,
                                    description = stringResource(getPrefStringBySettingsItem(item)),
                                    popupItems = AppTheme.entries.map { Selectable(it, it == item) },
                                    popupItemDisplayText = { UiText.StringResource(getPrefStringBySettingsItem(it)) },
                                    popupItemKey = { it.name },
                                    popupItemClick = { selectedTheme ->
                                        onEvent(SettingsUIEvent.OnAppThemeSelected(selectedTheme.item))
                                    },
                                )
                            }
                            is WeightUnit -> {
                                ExpandableSettingsItem(
                                    shape = LiftingTheme.shapes.listShapes(entries.value.size,index),
                                    title = stringResource(com.lifting.app.core.ui.R.string.settings_screen_weight_unit_title),
                                    icon = LiftingTheme.icons.dumbbell,
                                    description = stringResource(getPrefStringBySettingsItem(item)),
                                    popupItems = WeightUnit.entries.map { Selectable(it, it == item) },
                                    popupItemDisplayText = { UiText.StringResource(getPrefStringBySettingsItem(it)) },
                                    popupItemKey = { it.name },
                                    popupItemClick = { selectedUnit ->
                                        onEvent(SettingsUIEvent.OnWeightUnitSelected(selectedUnit.item))
                                    }
                                )
                            }
                            is DistanceUnit -> {
                                ExpandableSettingsItem(
                                    shape = LiftingTheme.shapes.listShapes(entries.value.size,index),
                                    title = stringResource(com.lifting.app.core.ui.R.string.settings_screen_distance_unit_title),
                                    icon = LiftingTheme.icons.run,
                                    description = stringResource(getPrefStringBySettingsItem(item)),
                                    popupItems = DistanceUnit.entries.map { Selectable(it, it == item) },
                                    popupItemDisplayText = { UiText.StringResource(getPrefStringBySettingsItem(it)) },
                                    popupItemKey = { it.name },
                                    popupItemClick = { onEvent(SettingsUIEvent.OnDistanceUnitSelected(it.item)) }
                                )
                            }
                            is AppLanguage -> {
                                fun onLanguageSelected(language: AppLanguage) {
                                    onEvent(SettingsUIEvent.OnAppLanguageSelected(language))
                                    AppLocale.changeLocale(language, context)
                                }

                                ExpandableSettingsItem(
                                    shape = LiftingTheme.shapes.listShapes(entries.value.size,index),
                                    title = stringResource(com.lifting.app.core.ui.R.string.settings_screen_app_language_title),
                                    icon = LiftingTheme.icons.language,
                                    description = stringResource(getPrefStringBySettingsItem(item)),
                                    popupItems = AppLanguage.entries.map { Selectable(it, it == item) },
                                    popupItemDisplayText = { UiText.StringResource(getPrefStringBySettingsItem(it)) },
                                    popupItemKey = { it.name },
                                    popupItemClick = { onLanguageSelected(it.item) }
                                )
                            }

                            SettingsItem.BACKUP_DATA -> {
                                SettingsItem(
                                    shape = LiftingTheme.shapes.listShapes(entries.value.size,index),
                                    icon = LiftingTheme.icons.folder,
                                    text = stringResource(com.lifting.app.core.ui.R.string.settings_screen_backup_data_title),
                                    description = stringResource(com.lifting.app.core.ui.R.string.settings_screen_backup_data_description_title),
                                    onClick = {
                                        backupLauncher.launch(backupName(System.currentTimeMillis()))
                                    }
                                )
                            }

                            SettingsItem.RESTORE_DATA -> {
                                SettingsItem(
                                    shape = LiftingTheme.shapes.listShapes(entries.value.size,index),
                                    icon = LiftingTheme.icons.restore,
                                    text = stringResource(com.lifting.app.core.ui.R.string.settings_screen_restore_data_title),
                                    description = stringResource(com.lifting.app.core.ui.R.string.settings_screen_restore_data_description_title),
                                    onClick = {
                                        restoreLauncher.launch(arrayOf("*/*"))
                                    }
                                )
                            }

                            SettingsItem.WRITE_REVIEW -> {
                                SettingsItem(
                                    shape = LiftingTheme.shapes.listShapes(entries.value.size,index),
                                    icon = LiftingTheme.icons.thumbsUpDown,
                                    text = stringResource(com.lifting.app.core.ui.R.string.settings_screen_review_title),
                                    description = stringResource(com.lifting.app.core.ui.R.string.settings_screen_review_description),
                                    onClick = {
                                        onEvent(SettingsUIEvent.OnWriteReviewClicked)
                                    }
                                )
                            }

                            SettingsItem.SUGGESTION_BUG -> {
                                SettingsItem(
                                    shape = LiftingTheme.shapes.listShapes(entries.value.size,index),
                                    icon = LiftingTheme.icons.mail,
                                    text = stringResource(com.lifting.app.core.ui.R.string.settings_screen_suggestions_bugs_title),
                                    description = stringResource(com.lifting.app.core.ui.R.string.settings_screen_suggestions_bugs_description),
                                    onClick = {
                                        onEvent(SettingsUIEvent.OnSuggestionAndBugClicked)
                                    }
                                )
                            }
                        }
                    }

                }
            }
        }
    )
}

private const val BACKUP_MIME_TYPE = "application/octet-stream"
private fun backupName(timeMillis: Long): String = "lifting_backup_${timeMillis}.backup"
