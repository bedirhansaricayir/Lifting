package com.lifting.app.feature.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import com.lifting.app.core.data.repository.workouts.WorkoutsRepository
import com.lifting.app.feature.calendar.models.CalendarMonth
import com.lifting.app.feature.calendar.paging.CalendarPagingDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.Year
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 09.03.2025
 */

@HiltViewModel
class CalendarViewModel @Inject constructor(
    workoutsRepository: WorkoutsRepository
) : ViewModel() {

    private var _calendarFlow: Flow<PagingData<Any>> = flow { }
    var calendarFlow = _calendarFlow
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            replay = 1
        )

    val workoutsCounts = workoutsRepository.getWorkoutsCount()
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            replay = 1
        )


    init {
        viewModelScope.launch {
            calendarFlow = Pager(
                config = PagingConfig(pageSize = 1, prefetchDistance = 1),
                initialKey = Year.now().value,
                pagingSourceFactory = {
                    CalendarPagingDataSource(
                        startYear = Year.now().value,
                        firstDayOfWeek = DayOfWeek.MONDAY
                    )
                }
            ).flow
                .map(::mapCalendar)
                .cachedIn(viewModelScope)
                .shareIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(),
                    replay = 1
                )
        }
    }

    private fun mapCalendar(pagingData: PagingData<CalendarMonth>): PagingData<Any> {
        return pagingData.insertSeparators { before, after ->
            if (after != null && before?.year != after.year) {
                after.year
            } else {
                null
            }
        }
    }
}
