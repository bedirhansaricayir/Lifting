package com.lifting.app.feature.settings

import androidx.compose.runtime.Immutable
import com.lifting.app.core.base.viewmodel.State

/**
 * Created by bedirhansaricayir on 16.05.2025
 */

@Immutable
data class SettingsUIState(val listUi: Map<Int, List<Any>> = mapOf()): State

enum class SettingsItem {
    BACKUP_DATA,
    RESTORE_DATA,
    WRITE_REVIEW,
    SUGGESTION_BUG
}