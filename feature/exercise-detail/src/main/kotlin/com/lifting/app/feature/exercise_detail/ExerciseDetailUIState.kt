package com.lifting.app.feature.exercise_detail

import androidx.compose.runtime.Immutable
import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.model.Exercise
import com.lifting.app.core.model.LogEntriesWithWorkout
import com.lifting.app.core.model.Selectable
import com.lifting.app.core.ui.common.UiText
import com.lifting.app.feature.exercise_detail.ChartType.HEAVIEST_WEIGHT
import com.lifting.app.feature.exercise_detail.ChartType.MAX_REPS
import com.lifting.app.feature.exercise_detail.ChartType.TOTAL_VOLUME

/**
 * Created by bedirhansaricayir on 02.03.2025
 */

@Immutable
internal data class ExerciseDetailUIState(
    val exercise: Exercise? = null,
    val logEntries: List<LogEntriesWithWorkout> = emptyList(),
    val chartData: Map<ChartType, Pair<ChartPeriod, List<ChartData>>> = emptyMap(),
    val periods: Map<ChartType, List<Selectable<ChartPeriod>>> = emptyMap(),
    val isDropdownVisible: Map<ChartType, Boolean> = emptyMap(),
) : State

internal data class ChartData(
    val label: String,
    val value: Double,
)

internal enum class ChartType(
    val title: UiText
) {
    HEAVIEST_WEIGHT(
        title = UiText.StringResource(com.lifting.app.core.ui.R.string.heaviest_weight)
    ),
    TOTAL_VOLUME(
        title = UiText.StringResource(com.lifting.app.core.ui.R.string.total_volume)
    ),
    MAX_REPS(
        title = UiText.StringResource(com.lifting.app.core.ui.R.string.max_reps)
    );

    companion object {
        operator fun invoke(): List<ChartType> = ChartType.entries
    }
}

internal enum class ChartPeriod(
    val title: UiText
) {
    WEEKLY(
        title = UiText.StringResource(com.lifting.app.core.ui.R.string.chart_period_weekly)
    ),
    MONTHLY(
        title = UiText.StringResource(com.lifting.app.core.ui.R.string.chart_period_monthly)
    ),
    YEARLY(
        title = UiText.StringResource(com.lifting.app.core.ui.R.string.chart_period_yearly)
    );

    companion object {
        operator fun invoke() = mapOf<ChartType, ChartPeriod>(
            HEAVIEST_WEIGHT to WEEKLY,
            TOTAL_VOLUME to WEEKLY,
            MAX_REPS to WEEKLY
        )

        fun getPeriods(): List<ChartPeriod> = ChartPeriod.entries
    }
}
