package com.lifting.app.feature_home.presentation.tracker


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.NumberPicker
import com.lifting.app.R
import com.lifting.app.common.util.toLocaleFormat
import com.lifting.app.feature_home.domain.model.ChartState
import com.lifting.app.feature_home.presentation.tracker.components.Chart
import com.lifting.app.feature_home.presentation.tracker.components.TimeRangePicker
import com.lifting.app.feature_home.presentation.tracker.components.DatePickerDialog
import com.lifting.app.feature_home.presentation.tracker.components.ExpandableTableCard
import com.lifting.app.feature_home.presentation.tracker.components.FilterChip
import com.lifting.app.feature_home.presentation.tracker.components.FiltersChip
import com.lifting.app.feature_home.presentation.tracker.components.MultiFloatingActionButton
import com.lifting.app.feature_home.presentation.tracker.components.MultiLineTextField
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
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TrackerScreen(
    state: TrackerPageUiState,
    onEvent: (TrackerPageEvent) -> Unit,
) {
    var openSetFilterModalBottomSheet by remember { mutableStateOf(false) }
    var openAddToChartModalBottomSheet by remember { mutableStateOf(false) }
    var selectedTimeRange by remember { mutableStateOf(TimeRange.SEVEN_DAYS) }
    var selectedSortBy by remember { mutableStateOf(SortBy.DATE) }
    var isCircleVisible by remember { mutableStateOf(false) }
    var isValuesVisible by remember { mutableStateOf(true) }
    var setDrawFilled by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var selectedDayValue by remember { mutableStateOf<ChartState?>(null) }
    var isChartPage by remember { mutableStateOf(true) }

    val pagerState = rememberPagerState()
    LaunchedEffect(key1 = pagerState.currentPage) {
        isChartPage = pagerState.currentPage == 0
    }
    LaunchedEffect(key1 = state.isExistSameDateError){
        if (state.isExistSameDateError) {
            // TODO - Same data available error will be shown
            onEvent(TrackerPageEvent.UserViewedTheError)
        }
    }
    LaunchedEffect(key1 = Unit){
        state.chartState.forEach {
            if (it.date.isEqual(LocalDate.now())){
                selectedDayValue = it
            }
        }
    }
    if (openSetFilterModalBottomSheet) {
        SetFilterModalBottomSheet(
            state = state,
            onDismiss = { openSetFilterModalBottomSheet = false },
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
    if (openAddToChartModalBottomSheet) {
        AddToChartModalBottomSheet(
            onDismiss = { openAddToChartModalBottomSheet = false },
            onSaveButtonClicked = { selectedDay, selectedValue, userDesc ->
                if (state.isExistSameDateError) Log.d("zooorttt","zort") else
                onEvent(TrackerPageEvent.OnDialogButtonClicked(selectedDay,selectedValue,userDesc))
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
                if (isChartPage) {
                    MultiFloatingActionButton(
                        fabIcon = FabIcon(iconRes = R.drawable.fab_add, iconRotate = 45f),
                        onFabItemClicked = {
                            when (it.fabItemType) {
                                FabItemType.INSERT -> openAddToChartModalBottomSheet = !openAddToChartModalBottomSheet
                                FabItemType.FILTER -> openSetFilterModalBottomSheet = !openSetFilterModalBottomSheet
                            }
                        },
                    )
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = grey50)
                    .padding(it.calculateBottomPadding())
            ) {
                HorizontalPager(pageCount = 2, state = pagerState) { page ->
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
                        when (page) {
                            CHART -> {
                                TimeRangePicker(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                                    selectedTimeRange = state.getTimeRange()
                                ) { timeRange ->
                                    selectedTimeRange = timeRange
                                    onEvent(
                                        TrackerPageEvent.OnTimeRangeClicked(
                                            selectedSortBy,
                                            timeRange
                                        )
                                    )
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
                                ) { selectedChartState, b, c ->
                                    Log.d("OnChartValueSelected", "$selectedChartState $b $c")
                                    scope.launch {
                                        pagerState.animateScrollToPage(2)
                                    }
                                    //selectedDayValue = selectedChartState
                                }

                            }

                            CALENDAR -> {
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

                                Spacer(modifier = Modifier.height(16.dp))

                                selectedDayValue?.let { selectedState ->
                                    ExpandableTableCard(
                                        chartState = selectedState
                                    )
                                }
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
                        val color = if (pagerState.currentPage == iteration) Color.White else Color.White.copy(alpha = 0.5f)
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetFilterModalBottomSheet(
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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToChartModalBottomSheet(
    onDismiss: () -> Unit,
    onSaveButtonClicked: (selectedDay: LocalDate,selectedValue: Float,userDesc: String) -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    var selectedValue by remember { mutableStateOf(75) }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<LocalDate>(LocalDate.now()) }
    var userDesc by remember { mutableStateOf("") }
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        containerColor = black20,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = stringResource(id = R.string.add_to_chart_label),
                style = MaterialTheme.typography.titleSmall,
                color = White40,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 8.dp, vertical = 16.dp)
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true }
                .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${stringResource(id = R.string.selected_date_label)}: ${selectedDate.toLocaleFormat()}",
                    style = MaterialTheme.typography.labelMedium
                )
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Edit date")
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { selectedValue += 1 },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = black20,
                        disabledContentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Arttır Butonu"
                    )
                }
                NumberPicker(
                    textStyle = TextStyle(color = Color.White),
                    value = selectedValue,
                    onValueChange = {
                        selectedValue = it
                    }, range = 0..200,
                    dividersColor = MaterialTheme.colorScheme.primary
                )
                IconButton(
                    onClick = { selectedValue -= 1 },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = black20,
                        disabledContentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Azalt Butonu"
                    )
                }
            }

            MultiLineTextField(
                modifier = Modifier,
                value = userDesc,
                onValueChanged = { userDesc = it })

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                onClick = { onSaveButtonClicked(selectedDate,selectedValue.toFloat(),userDesc) },
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

const val CHART = 0
const val CALENDAR = 1

