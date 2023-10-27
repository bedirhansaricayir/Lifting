package com.lifting.app.feature_tracker.domain.model

import androidx.annotation.StringRes
import com.lifting.app.R

enum class AnalysisTimeRange(@StringRes val time: Int) {
    TIMERANGE_7DAYS(R.string.short_seven_days),
    TIMERANGE_30DAYS(R.string.short_thirty_days),
    TIMERANGE_60DAYS(R.string.short_sixty_days),
    TIMERANGE_90DAYS(R.string.short_ninety_days),
    TIMERANGE_1YEAR(R.string.short_one_year)
}