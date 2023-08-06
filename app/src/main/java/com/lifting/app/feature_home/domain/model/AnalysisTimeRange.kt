package com.lifting.app.feature_home.domain.model

import androidx.annotation.StringRes
import com.lifting.app.R

enum class AnalysisTimeRange(@StringRes val time: Int,val minusDay: Long) {
    TIMERANGE_7DAYS(R.string.short_seven_days,7),
    TIMERANGE_30DAYS(R.string.short_thirty_days,30),
    TIMERANGE_60DAYS(R.string.short_sixty_days,60),
    TIMERANGE_90DAYS(R.string.short_ninety_days,90),
    TIMERANGE_1YEAR(R.string.short_one_year,365)
}