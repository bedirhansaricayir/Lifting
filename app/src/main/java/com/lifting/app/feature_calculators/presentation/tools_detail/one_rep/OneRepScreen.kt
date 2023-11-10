package com.lifting.app.feature_calculators.presentation.tools_detail.one_rep

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lifting.app.R
import com.lifting.app.feature_home.presentation.components.CommonTopBar
import okhttp3.internal.format

@Composable
fun OneRepScreen(
    state: OneRepScreenState,
    onEvent: (OneRepScreenEvent) -> Unit,
    onBackNavigationIconClicked: () -> Unit
) {
    OneRepScreenContent(
        state = state,
        onWeightValueChanged = { onEvent(OneRepScreenEvent.OnWeightValueChanged(it)) },
        onRepValueChanged = { onEvent(OneRepScreenEvent.OnRepValueChanged(it)) },
        onBackNavigationIconClicked = onBackNavigationIconClicked
    )
}

@Composable
fun OneRepScreenContent(
    state: OneRepScreenState,
    onWeightValueChanged: (Float) -> Unit,
    onRepValueChanged: (Int) -> Unit,
    onBackNavigationIconClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CommonTopBar(
            title = R.string.rm_calculator_label,
            onBackNavigationIconClicked = onBackNavigationIconClicked
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            WeightInputField(
                modifier = Modifier.weight(0.6f),
                text = state.weight,
                onValueChanged = { weight ->
                    onWeightValueChanged(weight)
                }
            )
            Spacer(modifier = Modifier.width(24.dp))
            RepInputField(
                modifier = Modifier.weight(0.3f),
                value = state.rep,
                onValueChanged = { rep ->
                    onRepValueChanged(rep)
                }
            )
        }

        TableSection(
            isVisible = state.weight != 0f,
            modifier = Modifier,
            itemList = state.oneRepMax
        )
    }
}

@Composable
fun TableSection(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    itemList: List<Double>? = emptyList()
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            item {
                Row {
                    TableCell(text = "RM", weight = 1f)
                    TableCell(text = stringResource(id = R.string.weight_label), weight = 1f)
                    TableCell(text = stringResource(id = R.string.percent_label), weight = 1f)
                }
            }
            itemList?.let { value ->
                var currentPercentage = 100.0
                items(value.size) { index ->
                    OneRepListItem(
                        rm = index + 1,
                        value = value[index],
                        percent = currentPercentage
                    )
                    currentPercentage -= 5
                    if (value.size > index + 1) {
                        Divider(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, MaterialTheme.colorScheme.onSurface)
            .weight(weight)
            .padding(8.dp)
    )
}

@Composable
fun OneRepListItem(
    modifier: Modifier = Modifier,
    rm: Int,
    value: Double,
    percent: Double
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$rm",
            textAlign = TextAlign.Left,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            modifier = Modifier,
            textAlign = TextAlign.Center,
            text = format("%.2f", value),
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = "${percent}%",
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

@Composable
fun WeightInputField(
    modifier: Modifier = Modifier,
    text: Float,
    onValueChanged: (Float) -> Unit,
) {
    TextField(
        modifier = modifier,
        value = if (text.toInt() == 0) "" else text.toString(),
        onValueChange = {
            val filtered = it.filter { symbol -> symbol.isDigit() }
            val parsed = it.toFloatOrNull()
            if (parsed != null && parsed <= 500) {
                onValueChanged(parsed)
            }

        },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.weight_label),
                style = MaterialTheme.typography.titleSmall,
                color = Color.LightGray
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedTextColor = Color.White,
            disabledTextColor = Color.White,
            unfocusedTextColor = Color.White,
            unfocusedIndicatorColor = Color.White,

            )
    )
}

@Composable
fun RepInputField(
    modifier: Modifier = Modifier,
    value: Int,
    onValueChanged: (Int) -> Unit,
) {
    var text by remember(value) { mutableStateOf(value.toString()) }

    TextField(
        modifier = modifier,
        value = text,
        onValueChange = { raw ->
            val parsed = raw.toIntOrNull() ?: 1
            if (parsed <= 20) {
                text = raw
                onValueChanged(parsed)
            }
        },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedTextColor = Color.White,
            disabledTextColor = Color.White,
            unfocusedTextColor = Color.White,
            unfocusedIndicatorColor = Color.White,
        ),
        trailingIcon = {
            Text(
                text = stringResource(id = R.string.rep_label),
                style = MaterialTheme.typography.titleSmall,
                color = Color.LightGray
            )
        }


    )
}