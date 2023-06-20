package com.fitness.app.presentation.home.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fitness.app.R
import com.fitness.app.ui.theme.White40
import com.fitness.app.ui.theme.grey30

@Composable
fun CustomCreatedProgramDialog(
    dialogState: Boolean,
    program: String,
    onDissmiss: () -> Unit
) {
    val showDialog = remember { mutableStateOf(dialogState) }
    if (showDialog.value) {
        Dialog(
            onDismissRequest = { onDissmiss.invoke() },
            properties = DialogProperties(dismissOnClickOutside = true, dismissOnBackPress = true)
        ) {
            CustomProgramDialogContent(stringResource(id = R.string.KişiselProgram),program)
        }
    }
}

@Composable
fun CustomProgramDialogContent(
    title:String,
    program: String
) {
    val verticalScroll = rememberScrollState()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(verticalScroll),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = grey30
        ),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 12.dp, horizontal = 8.dp),
            text = title,
            style = MaterialTheme.typography.titleSmall
        )
        Box(modifier = Modifier) {
            Text(modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp),text = program, style = MaterialTheme.typography.labelMedium,color = White40
            )
        }
    }
}