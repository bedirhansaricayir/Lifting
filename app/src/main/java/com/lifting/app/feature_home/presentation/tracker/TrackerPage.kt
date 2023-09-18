package com.lifting.app.feature_home.presentation.tracker


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lifting.app.R
import com.lifting.app.feature_home.domain.model.ChartState
import com.lifting.app.feature_home.presentation.tracker.components.Chart
import com.lifting.app.feature_home.presentation.tracker.components.TimeRangePicker
import com.lifting.app.feature_home.presentation.tracker.components.CustomTrackingDialog
import com.lifting.app.feature_home.presentation.tracker.components.ExpandableTableCard
import com.lifting.app.feature_home.presentation.tracker.components.FilterChip
import com.lifting.app.feature_home.presentation.tracker.components.FiltersChip
import com.lifting.app.feature_home.presentation.tracker.components.MultiFloatingActionButton
import com.lifting.app.feature_home.presentation.tracker.components.SelectableSortBy
import com.lifting.app.feature_home.presentation.tracker.components.SortBy
import com.lifting.app.feature_home.presentation.tracker.components.TimeRange
import com.lifting.app.feature_home.presentation.tracker.components.custom_calendar.Calendar
import com.lifting.app.feature_home.presentation.tracker.components.custom_fab.FabIcon
import com.lifting.app.feature_home.presentation.tracker.components.custom_fab.FabItemType
import com.lifting.app.theme.White40
import com.lifting.app.theme.black20
import com.lifting.app.theme.grey10
import com.lifting.app.theme.grey50
import java.time.LocalDate

@OptIn(ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TrackerScreen(
    state: TrackerPageUiState,
    onEvent: (TrackerPageEvent) -> Unit,
) {
    var openModalBottomSheet by remember { mutableStateOf(false) }
    var selectedTimeRange by remember { mutableStateOf(TimeRange.SEVEN_DAYS) }
    var selectedSortBy by remember { mutableStateOf(SortBy.DATE) }
    var isCircleVisible by remember { mutableStateOf(false) }
    var isValuesVisible by remember { mutableStateOf(true) }
    var setDrawFilled by remember { mutableStateOf(false) }
    var onFabClick by remember { mutableStateOf(false) }
    var selectedDayValue by remember { mutableStateOf<ChartState?>(null) }

    if (openModalBottomSheet) {
        CustomModalBottomSheet(
            state = state,
            onDismiss = { openModalBottomSheet = false },
            onSortByClicked = {
                selectedSortBy = it
                onEvent(TrackerPageEvent.OnSortByClicked(selectedSortBy, selectedTimeRange))
            },
            onFilterChipClicked = { selectedFilterChips ->
                onEvent(TrackerPageEvent.OnFilterChipClicked(selectedFilterChips))
                isCircleVisible = false
                isValuesVisible = false
                setDrawFilled = false
                selectedFilterChips.forEach { filterChip ->
                    when (filterChip) {
                        FilterChip.CIRCLE -> isCircleVisible = true
                        FilterChip.VALUES -> isValuesVisible = true
                        FilterChip.FILLED -> setDrawFilled = true
                    }
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier.statusBarsPadding(),
            floatingActionButton = {
                MultiFloatingActionButton(
                    fabIcon = FabIcon(iconRes = R.drawable.fab_add, iconRotate = 45f),
                    onFabItemClicked = {
                        when (it.fabItemType) {
                            FabItemType.INSERT -> onFabClick = !onFabClick
                            FabItemType.FILTER -> openModalBottomSheet = !openModalBottomSheet
                        }
                    },
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = grey50)
                    .padding(it.calculateBottomPadding())
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        text = stringResource(id = R.string.Analiz),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

                    TimeRangePicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                        selectedTimeRange = state.getTimeRange()
                    ) { timeRange ->
                        selectedTimeRange = timeRange
                        onEvent(TrackerPageEvent.OnTimeRangeClicked(selectedSortBy, timeRange))
                    }

                    Chart(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        chartState = state.chartState,
                        isCircleVisible = isCircleVisible,
                        isValuesVisible = isValuesVisible,
                        isMoveViewToAnimated = false,
                        setDrawFilled = setDrawFilled
                    ) { selectedChartState,b,c ->
                        Log.d("OnChartValueSelected", "$selectedChartState $b $c")
                        //selectedDayValue = selectedChartState
                    }
                    Calendar(
                        modifier = Modifier,
                        isValueAvailable = state.chartState.map { chartState ->
                            chartState.date
                        },
                        onDateClickListener = { date ->
                            state.chartState.forEach { chartState ->
                                if (chartState.date.isEqual(date)) {
                                    selectedDayValue = chartState
                                }
                            }
                        }
                    )

                    selectedDayValue?.let { selectedState -> ExpandableTableCard(chartState = selectedState) }


                }
            }
        }

        if (onFabClick) {
            CustomTrackingDialog(
                dialogState = onFabClick,
                onDissmiss = { onFabClick = !onFabClick },
                onSaveButtonClicked = {
                    onEvent(
                        TrackerPageEvent.OnDialogButtonClicked(
                            localDate = LocalDate.now(),
                            data = it.toFloat(),
                            desc = "İlk deneme"
                        )
                    )
                    onFabClick = !onFabClick
                }
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomModalBottomSheet(
    state: TrackerPageUiState,
    onDismiss: () -> Unit,
    onSortByClicked: (sortBy: SortBy) -> Unit,
    onFilterChipClicked: (filterChip: MutableList<FilterChip>) -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        containerColor = black20,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.filters_label),
                style = MaterialTheme.typography.titleSmall,
                color = White40,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 8.dp, vertical = 16.dp)
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = R.string.sort_by),
                style = MaterialTheme.typography.labelMedium,
                color = grey10
            )
            SelectableSortBy(selectedSortBy = state.getSortBy()) { sortBy ->
                onSortByClicked(sortBy)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = R.string.graphic_settings),
                style = MaterialTheme.typography.labelMedium,
                color = grey10
            )

            FiltersChip(state.selectedFilterChip) { selectedChip ->
                onFilterChipClicked(selectedChip)
            }
        }
    }
}

