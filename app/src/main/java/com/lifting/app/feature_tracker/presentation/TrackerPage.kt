package com.lifting.app.feature_tracker.presentation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lifting.app.R
import com.lifting.app.common.components.CommonAlertDialog
import com.lifting.app.common.constants.Constants.Companion.CALENDAR
import com.lifting.app.common.constants.Constants.Companion.CHART
import com.lifting.app.common.util.toLocalDate
import com.lifting.app.common.util.toLocaleFormat
import com.lifting.app.feature_tracker.domain.model.ChartState
import com.lifting.app.feature_tracker.presentation.calendar.Calendar
import com.lifting.app.feature_tracker.presentation.calendar.CalendarViewModel
import com.lifting.app.feature_tracker.presentation.components.Chart
import com.lifting.app.feature_tracker.presentation.components.TimeRangePicker
import com.lifting.app.feature_tracker.presentation.components.DatePickerDialog
import com.lifting.app.feature_tracker.presentation.components.ExpandableList
import com.lifting.app.feature_tracker.presentation.components.FilterChip
import com.lifting.app.feature_tracker.presentation.components.FiltersChip
import com.lifting.app.feature_tracker.presentation.components.MultiFloatingActionButton
import com.lifting.app.feature_tracker.presentation.components.MultiLineTextField
import com.lifting.app.feature_tracker.presentation.components.SelectableSortBy
import com.lifting.app.feature_tracker.presentation.components.SortBy
import com.lifting.app.feature_tracker.presentation.components.TimeRange
import com.lifting.app.feature_tracker.presentation.components.UserDataInput
import com.lifting.app.feature_tracker.presentation.components.custom_fab.FabIcon
import com.lifting.app.feature_tracker.presentation.components.custom_fab.FabItemType
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TrackerScreen(
    state: TrackerPageUiState,
    onEvent: (TrackerPageEvent) -> Unit,
) {
    TrackerPageContent(
        state = state,
        addToChartBottomSheetClicked = { onEvent(TrackerPageEvent.AddToChartBottomSheetClicked) },
        setFilterBottomSheetClicked = { onEvent(TrackerPageEvent.SetFilterBottomSheetClicked) },
        onTimeRangeClicked = { sortBy, timeRange ->
            onEvent(TrackerPageEvent.OnTimeRangeClicked(sortBy, timeRange))
        },
        userViewedTheError = { onEvent(TrackerPageEvent.UserViewedTheError) },
        onUpdateClicked = { chartState ->
            onEvent(TrackerPageEvent.OnDialogUpdateButtonClicked(chartState))
        },
        setFilterBottomSheetOnDismiss = { onEvent(TrackerPageEvent.SetFilterBottomSheetOnDismiss) },
        onSortByClicked = { sortBy, timeRange ->
            onEvent(TrackerPageEvent.OnSortByClicked(sortBy, timeRange))
        },
        onFilterChipClicked = { selectedChips ->
            onEvent(TrackerPageEvent.OnFilterChipClicked(selectedChips))
        },
        addToChartBottomSheetOnDismiss = { onEvent(TrackerPageEvent.AddToChartBottomSheetOnDismiss) },
        onSaveButtonClicked = { chartState ->
            onEvent(TrackerPageEvent.OnSaveButtonClicked(chartState))
        }
    )


}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun TrackerPageContent(
    state: TrackerPageUiState,
    addToChartBottomSheetClicked: () -> Unit,
    setFilterBottomSheetClicked: () -> Unit,
    onTimeRangeClicked: (sb: SortBy, tr: TimeRange) -> Unit,
    userViewedTheError: () -> Unit,
    onUpdateClicked: (state: ChartState) -> Unit,
    setFilterBottomSheetOnDismiss: () -> Unit,
    onSortByClicked: (sb: SortBy, tr: TimeRange) -> Unit,
    onFilterChipClicked: (filterChip: MutableList<FilterChip>) -> Unit,
    addToChartBottomSheetOnDismiss: () -> Unit,
    onSaveButtonClicked: (chartState: ChartState) -> Unit,
) {
    var selectedTimeRange by remember { mutableStateOf(TimeRange.SEVEN_DAYS) }
    var selectedSortBy by remember { mutableStateOf(SortBy.DATE) }
    var isCircleVisible by remember { mutableStateOf(false) }
    var isValuesVisible by remember { mutableStateOf(true) }
    var setDrawFilled by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState { 2 }
    var isChartPage by remember { mutableStateOf(true) }
    var pendingEventDataHolder by remember { mutableStateOf<ChartState?>(null) }

    LaunchedEffect(key1 = pagerState.currentPage) {
        isChartPage = pagerState.currentPage == CHART
    }

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        floatingActionButton = {
            if (isChartPage) {
                MultiFloatingActionButton(
                    fabIcon = FabIcon(iconRes = R.drawable.fab_add, iconRotate = 45f),
                    onFabItemClicked = {
                        when (it.fabItemType) {
                            FabItemType.INSERT -> addToChartBottomSheetClicked()
                            FabItemType.FILTER -> setFilterBottomSheetClicked()
                        }
                    },
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(it.calculateBottomPadding())
        ) {
            HorizontalPager(state = pagerState) { page ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        text = stringResource(id = if (page == CHART) R.string.chart_page_label else R.string.calendar_page_label),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                    when (page) {
                        CHART -> {
                            TimeRangePicker(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                                selectedTimeRange = state.getTimeRange(),
                                onTimeRangeSelected = { timeRange ->
                                    selectedTimeRange = timeRange
                                    onTimeRangeClicked(selectedSortBy, timeRange)
                                }
                            )

                            Chart(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                chartState = state.chartState,
                                isCircleVisible = isCircleVisible,
                                isValuesVisible = isValuesVisible,
                                isMoveViewToAnimated = false,
                                setDrawFilled = setDrawFilled,
                                onValueSelected = { selectedChartState ->
                                    //selectedDayValue = selectedChartState
                                }
                            )

                        }

                        CALENDAR -> {
                            val viewModel: CalendarViewModel = hiltViewModel()
                            val state = viewModel.state.collectAsState().value

                            Text(
                                modifier = Modifier.padding(
                                    PaddingValues(
                                        start = 15.dp,
                                        top = 10.dp,
                                        bottom = 10.dp
                                    )
                                ),
                                text = state.currMonthViewed,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.W600,
                                color = Color.LightGray,
                            )

                            Calendar(
                                updateCurrMonthViewed = viewModel::updateCurrentMonthViewed,
                                updateCurrDateViewed = viewModel::updateCurrentDateViewed,
                                getDateEventCount = viewModel::checkIfDateHasEvent,
                                checkIfDateSelected = viewModel::isDateSelected,
                                resetCurrDate = viewModel::resetToCurrentDate,
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            ExpandableList(
                                list = state.chartState,
                                currentDate = state.currDateViewed.toLocalDate()!!,
                            )
                        }
                    }
                }

            }
            Row(
                Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(2) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.5f
                        )
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp)
                            .clickable {
                                scope.launch {
                                    pagerState.animateScrollToPage(iteration)
                                }
                            }
                    )
                }
            }
        }
    }


    CommonAlertDialog(
        dialogState = state.isExistSameDateError,
        title = R.string.date_already_exists_error_header,
        body = R.string.date_already_exists_error_body,
        confirmButtonTitle = R.string.date_already_exist_error_confirm_label,
        onDissmiss = userViewedTheError,
        onCancelClicked = { pendingEventDataHolder = null },
        onConfirmClicked = { onUpdateClicked(pendingEventDataHolder!!) }
    )

    SetFilterModalBottomSheet(
        bottomSheetVisibleState = state.setFilterBottomSheet,
        state = state,
        onDismiss = setFilterBottomSheetOnDismiss,
        onSortByClicked = {
            selectedSortBy = it
            onSortByClicked(selectedSortBy, selectedTimeRange)
        },
        onFilterChipClicked = { selectedFilterChips ->
            onFilterChipClicked(selectedFilterChips)
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

    AddToChartModalBottomSheet(
        bottomSheetVisibleState = state.addToChartBottomSheet,
        onDismiss = addToChartBottomSheetOnDismiss,
        onSaveButtonClicked = { chartState ->
            pendingEventDataHolder = chartState
            if (!state.isExistSameDateError) onSaveButtonClicked(chartState)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetFilterModalBottomSheet(
    bottomSheetVisibleState: Boolean,
    state: TrackerPageUiState,
    onDismiss: () -> Unit,
    onSortByClicked: (sortBy: SortBy) -> Unit,
    onFilterChipClicked: (filterChip: MutableList<FilterChip>) -> Unit
) {
    if (bottomSheetVisibleState) {
        val modalBottomSheetState = rememberModalBottomSheetState()
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = modalBottomSheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            dragHandle = { BottomSheetDefaults.DragHandle() },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(id = R.string.filters_label),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 8.dp, vertical = 16.dp)
                )
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(id = R.string.sort_by),
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.LightGray
                )
                SelectableSortBy(selectedSortBy = state.getSortBy()) { sortBy ->
                    onSortByClicked(sortBy)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(id = R.string.graphic_settings),
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.LightGray
                )

                FiltersChip(state.selectedFilterChip) { selectedChip ->
                    onFilterChipClicked(selectedChip)
                }
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToChartModalBottomSheet(
    bottomSheetVisibleState: Boolean,
    onDismiss: () -> Unit,
    onSaveButtonClicked: (chartState: ChartState) -> Unit

) {
    if (bottomSheetVisibleState) {
        val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        var showDatePicker by remember { mutableStateOf(false) }
        var selectedDate by remember { mutableStateOf<LocalDate>(LocalDate.now()) }
        var userDesc by remember { mutableStateOf("") }
        var userData by remember { mutableStateOf("") }
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = modalBottomSheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.add_to_chart_label),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 16.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${stringResource(id = R.string.selected_date_label)}: ${selectedDate.toLocaleFormat()}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Edit date"
                        )
                    }
                }
                if (showDatePicker) {
                    DatePickerDialog(
                        dialogState = true,
                        initialSelectedDate = selectedDate,
                        onDissmiss = { showDatePicker = false },
                        onDateSelected = {
                            selectedDate = it
                        }
                    )
                }

                UserDataInput(
                    value = userData,
                    onValueChanged = { newData ->
                        if (newData.startsWith("0")) userData = ""
                        if (newData.isEmpty()) userData = newData
                        if (newData.length == 5) userData =
                            newData.take(4) + "." + newData.last().toString()
                        if (newData.isBlank() || newData.matches(Regex("-?\\d{0,4}(\\.\\d{0,2})?"))) userData =
                            newData
                    },
                    decreaseClicked = {
                        val userDataFloat = userData.toFloatOrNull()
                        if (userDataFloat != null) userData = (userDataFloat - 1).toString()
                    },
                    increaseClicked = {
                        val userDataFloat = userData.toFloatOrNull()
                        if (userDataFloat != null) userData = (userDataFloat + 1).toString()
                    }
                )

                MultiLineTextField(
                    modifier = Modifier,
                    value = userDesc,
                    onValueChanged = { userDesc = it }
                )

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                    onClick = {
                        onSaveButtonClicked(ChartState(selectedDate, userData.toFloat(), userDesc))
                        onDismiss()
                    },
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background,
                    ),
                ) {
                    Text(
                        text = stringResource(id = R.string.button_save_label),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 40.dp)
                    )
                }


            }
        }
    }

}

