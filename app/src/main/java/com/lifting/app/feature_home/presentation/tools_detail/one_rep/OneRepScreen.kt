package com.lifting.app.feature_home.presentation.tools_detail.one_rep

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.lifting.app.R
import com.lifting.app.theme.grey10
import com.lifting.app.theme.grey50

@Composable
fun OneRepScreen(
    state: OneRepScreenState,
    onEvent: (OneRepScreenEvent) -> Unit
) {
    OneRepScreenContent(
        state = state,
        onWeightValueChanged = { onEvent(OneRepScreenEvent.OnWeightValueChanged(it)) }
    )
}

@Composable
fun OneRepScreenContent(
    state: OneRepScreenState,
    onWeightValueChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(grey50)
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WeightInputField(
            value = state.lift.weight,
            onValueChanged = { string ->
                onWeightValueChanged(string)
                if (string.length == 4) {
                    val newString = string.take(3) + "." + string.last().toString()
                    onWeightValueChanged(newString)
                }
                if (string.isBlank() || string.matches(Regex("-?\\d{0,3}(\\.\\d{0,2})?"))) onWeightValueChanged(string)
            }
        )
    }
}

@Composable
fun WeightInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
) {

    TextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChanged,
        maxLines = 1,
        singleLine = true,
        placeholder = {
            if (value == "")
            Text(
                text = stringResource(id = R.string.weight_label),
                style = MaterialTheme.typography.titleSmall,
                color = grey10
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
    )
}
