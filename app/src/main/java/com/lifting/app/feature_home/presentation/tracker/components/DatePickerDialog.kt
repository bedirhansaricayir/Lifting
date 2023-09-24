package com.lifting.app.feature_home.presentation.tracker.components


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.lifting.app.R
import com.lifting.app.common.util.toLocalDate
import com.lifting.app.common.util.toLong
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    dialogState: Boolean,
    initialSelectedDate: LocalDate,
    onDissmiss: () -> Unit,
    onDateSelected: (localDate: LocalDate) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialSelectedDate.toLong(),
        initialDisplayMode = DisplayMode.Picker
    )
    val dateValidator: (Long) -> Boolean = { selectedDateInMillis ->
        val selectedDate = selectedDateInMillis.toLocalDate()
        !selectedDate.isAfter(LocalDate.now())
    }

    var showDatePicker by remember { mutableStateOf(dialogState) }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { onDissmiss() },
            confirmButton = {
                TextButton(onClick = {
                    onDateSelected(datePickerState.selectedDateMillis!!.toLocalDate())
                    onDissmiss()
                }) {
                    Text(text = stringResource(id = R.string.confirm_label))
                }
            },
            dismissButton = {
                TextButton(onClick = { onDissmiss() }) {
                    Text(text = stringResource(id = R.string.dismiss_label))
                }
            },
            colors = DatePickerDefaults.colors(containerColor = Color.White)
        ) {
            DatePicker(
                state = datePickerState,
                dateValidator = dateValidator
            )
        }
    }
}

