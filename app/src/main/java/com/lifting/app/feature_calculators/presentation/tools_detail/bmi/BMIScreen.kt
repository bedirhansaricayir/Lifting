package com.lifting.app.feature_calculators.presentation.tools_detail.bmi

import androidx.compose.runtime.Composable
import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.*
import androidx.compose.ui.Modifier.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lifting.app.R
import com.lifting.app.feature_home.presentation.components.CommonTopBar
import com.lifting.app.theme.White40
import com.lifting.app.theme.black20
import com.lifting.app.theme.grey10
import com.lifting.app.theme.grey50
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BMIScreen(
    state: BMIScreenState,
    onEvent: (BMIScreenEvent) -> Unit,
    onBackNavigationIconClicked: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheet = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        //animationSpec = tween(500),
        skipHalfExpanded = true
    )

    LaunchedEffect(key1 = state.error) {
        if (state.error) {
            Toast.makeText(context,R.string.bmi_error_label,Toast.LENGTH_SHORT).show()
            onEvent(BMIScreenEvent.OnViewedErrorMessage)
        }
    }

    ModalBottomSheetLayout(
        sheetState = modalBottomSheet,
        sheetContent = {
            BottomSheetContent(
                sheetTitleStage = state.sheetTitle,
                sheetItemsList = state.sheetItemsList,
                onItemClicked = {
                    coroutineScope.launch { modalBottomSheet.hide() }
                    onEvent(BMIScreenEvent.OnSheetItemClicked(it))
                }
            )
        },
        sheetBackgroundColor = black20,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        content = {
            BMIScreenContent(
                state = state,
                onWeightUnitClicked = {
                    coroutineScope.launch { modalBottomSheet.show() }
                    onEvent(BMIScreenEvent.OnWeightTextClicked)
                },
                onHeightUnitClicked = {
                    coroutineScope.launch { modalBottomSheet.show() }
                    onEvent(BMIScreenEvent.OnHeightTextClicked)
                },
                onHeightValueClicked = {
                    onEvent(BMIScreenEvent.OnHeightValueClicked)
                },
                onWeightValueClicked = {
                    onEvent(BMIScreenEvent.OnWeightValueClicked)
                },
                onGoButtonClicked = {
                    onEvent(BMIScreenEvent.OnGoButtonClicked)
                },
                onNumberClicked = {
                    onEvent(BMIScreenEvent.OnNumberClicked(number = it))
                },
                onACButtonClicked = {
                    onEvent(BMIScreenEvent.OnAllClearButtonClicked)
                },
                onDeleteButtonClicked = {
                    onEvent(BMIScreenEvent.OnDeleteButtonClicked)
                },
                onRefreshClicked = {
                    onEvent(BMIScreenEvent.OnRefreshIconClicked)
                },
                onBackNavigationIconClicked = onBackNavigationIconClicked
            )
        }
    )
}

@Composable
fun BMIScreenContent(
    state: BMIScreenState,
    onWeightUnitClicked: () -> Unit,
    onHeightUnitClicked: () -> Unit,
    onHeightValueClicked: () -> Unit,
    onWeightValueClicked: () -> Unit,
    onGoButtonClicked: () -> Unit,
    onACButtonClicked: () -> Unit,
    onDeleteButtonClicked: () -> Unit,
    onNumberClicked: (String) -> Unit,
    onRefreshClicked: () -> Unit,
    onBackNavigationIconClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(grey50),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        CommonTopBar(
            title = R.string.bmi_calculator_label,
            onBackNavigationIconClicked = onBackNavigationIconClicked
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                UnitItem(text = R.string.weight_label, onClick = onWeightUnitClicked)
                InputUnitValue(
                    inputValue = state.weightValue,
                    inputUnit = state.weightUnit,
                    inputNoColor =
                    if (state.weightValueStage != WeightValueStage.INACTIVE) {
                        MaterialTheme.colorScheme.primary
                    } else Color.White,
                    onUnitValueClicked = onWeightValueClicked
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                UnitItem(text = R.string.height_label, onClick = onHeightUnitClicked)
                InputUnitValue(
                    inputValue = state.heightValue,
                    inputUnit = state.heightUnit,
                    inputNoColor = if (state.heightValueStage != HeightValueStage.INACTIVE) {
                        MaterialTheme.colorScheme.primary
                    } else Color.White,
                    onUnitValueClicked = onHeightValueClicked
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            if (state.shouldBMICardShow) {
                Crossfade(targetState = true, label = "") {
                    if (it) {
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            BMIResultCard(
                                bmi = state.bmi,
                                bmiStage = state.bmiStage,
                                bmiStageColor = when(state.bmiStage) {
                                    BMIRangeState.UNDERWEIGHT -> Color.Blue
                                    BMIRangeState.NORMAL -> Color.Green
                                    BMIRangeState.OVERWEIGHT -> Color.Red
                                    BMIRangeState.NONE -> Color.Unspecified
                                },
                                onRefreshClicked = onRefreshClicked
                            )
                        }
                    }
                }
            }
            else {
                Divider(color = grey10)
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    NumberKeyboard(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(7f),
                        onNumberClick = onNumberClicked
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(3f)
                    ) {
                        SymbolButton(symbol = "AC", onClick = onACButtonClicked)
                        SymbolButtonWithIcon(onClick = onDeleteButtonClicked)
                        SymbolButton(symbol = "GO", onClick = onGoButtonClicked)
                    }
                }
            }

        }
    }
}

@Composable
fun UnitItem(
    @StringRes text: Int,
    textColor: Color = Color.White,
    onClick: () -> Unit
) {
    Box(modifier = Modifier
        .clip(RoundedCornerShape(16.dp))
        .clickable { onClick() }
        .padding(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = text),
                color = textColor,
                style = MaterialTheme.typography.titleSmall
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Select Unit",
                tint = textColor
            )
        }
    }

}

@Composable
fun InputUnitValue(
    inputValue: String,
    inputUnit: String,
    inputNoColor: Color,
    onUnitValueClicked: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = inputValue,
            fontSize = 40.sp,
            color = inputNoColor,
            modifier = Modifier.clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) { onUnitValueClicked() }
        )
        Text(
            text = inputUnit,
            fontSize = 12.sp,
            color = grey10
        )
    }
}

@Composable
fun NumberKeyboard(
    modifier: Modifier = Modifier,
    onNumberClick: (String) -> Unit
) {
    Column(modifier = modifier) {
        val numberButtonList = listOf(
            "7", "8", "9", "4", "5", "6",
            "1", "2", "3", "", "0", "."
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3)
        ) {
            items(numberButtonList) { item ->
                NumberButton(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f),
                    number = item,
                    onClick = onNumberClick
                )
            }
        }
    }
}

@Composable
fun NumberButton(
    modifier: Modifier = Modifier,
    number: String,
    onClick: (String) -> Unit
) {
    if (number != "") {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .padding(10.dp)
                .clip(CircleShape)
                .clickable { onClick(number) }
        ) {
            Text(text = number, fontSize = 40.sp, color = Color.White)
        }
    }
}

@Composable
fun ColumnScope.SymbolButton(
    symbol: String,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(grey50)
            .clickable { onClick() }
            .padding(15.dp)
            .weight(1f)
            .aspectRatio(1f)
    ) {
        Text(text = symbol, fontSize = 26.sp, color = Color.White)
    }
}

@Composable
fun ColumnScope.SymbolButtonWithIcon(
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(grey50)
            .clickable { onClick() }
            .padding(15.dp)
            .weight(1f)
            .aspectRatio(1f)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Backspace Icon",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun BMIResultCard(
    bmi: Double,
    bmiStage: BMIRangeState = BMIRangeState.NORMAL,
    bmiStageColor: Color = Color.Green,
    onRefreshClicked: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(15.dp)
                )
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "$bmi", fontSize = 70.sp, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(15.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "BMI", fontSize = 40.sp, color = Color.Gray)
                    Text(text = stringResource(id = bmiStage.text), fontSize = 18.sp, color = bmiStageColor)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider(
                modifier = Modifier
                    .background(Color.Gray)
                    .shadow(elevation = 5.dp),
                thickness = 2.dp
            )
            Text(
                modifier = Modifier.padding(vertical = 25.dp),
                text = stringResource(id = R.string.bmi_information_label),
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 20.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = stringResource(id = R.string.underweight_label), color = Color.Blue)
                Text(text = stringResource(id = R.string.normal_label), color = Color.Green)
                Text(text = stringResource(id = R.string.overweight_label), color = Color.Red)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Divider(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Blue),
                    thickness = 5.dp
                )
                Divider(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Green),
                    thickness = 5.dp
                )
                Divider(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Red),
                    thickness = 5.dp
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "16.0", fontSize = 18.sp, color = Color.DarkGray)
                Text(text = "18.5", fontSize = 18.sp, color = Color.DarkGray)
                Text(text = "25.0", fontSize = 18.sp, color = Color.DarkGray)
                Text(text = "40.0", fontSize = 18.sp, color = Color.DarkGray)
            }
        }
        Icon(modifier = Modifier
            .align(Alignment.TopEnd)
            .clickable { onRefreshClicked() }
            .padding(top = 8.dp, end = 8.dp), imageVector = Icons.Default.Refresh, contentDescription = "Repeat")
    }

}

@Composable
fun BottomSheetContent(
    sheetTitleStage: SheetTitleStage,
    sheetItemsList: List<String>,
    onItemClicked: (String) -> Unit,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp),
        text = stringResource(id = if (sheetTitleStage == SheetTitleStage.WEIGHT) R.string.weight_label else R.string.height_label),
        style =MaterialTheme.typography.titleSmall,
        textAlign = TextAlign.Center,
        color = White40
    )
    sheetItemsList.forEach { item ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClicked(item) }
                .padding(8.dp)
        ) {
            Text(
                text = item,
                modifier = Modifier.padding(15.dp),
                color = White40
            )
        }
    }
}