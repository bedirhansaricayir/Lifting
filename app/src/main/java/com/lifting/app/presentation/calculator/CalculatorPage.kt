package com.lifting.app.presentation.calculator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.chargemap.compose.numberpicker.NumberPicker
import com.lifting.app.R
import com.lifting.app.core.constants.Constants.Companion.COK
import com.lifting.app.core.constants.Constants.Companion.COK_TEXT
import com.lifting.app.core.constants.Constants.Companion.ERKEK
import com.lifting.app.core.constants.Constants.Companion.HAFIF
import com.lifting.app.core.constants.Constants.Companion.HAFIF_TEXT
import com.lifting.app.core.constants.Constants.Companion.KADIN
import com.lifting.app.core.constants.Constants.Companion.ORTA
import com.lifting.app.core.constants.Constants.Companion.ORTA_TEXT
import com.lifting.app.core.constants.Constants.Companion.SEDANTER
import com.lifting.app.core.constants.Constants.Companion.SEDANTER_TEXT
import com.lifting.app.theme.White40
import com.lifting.app.theme.grey30
import com.lifting.app.theme.grey50
import com.lifting.app.theme.white30
import kotlinx.coroutines.launch


@Composable
fun CalculatorScreen() {
    var selectedCinsiyet by remember { mutableStateOf<String?>(null) }
    val cinsiyetSecim = listOf(ERKEK, KADIN)
    var selectedOption by remember { mutableStateOf<String?>(null) }
    val aktiviteSeviyesi = listOf(SEDANTER, HAFIF, ORTA, COK)
    val aktiviteSeviyesiDetay = listOf(SEDANTER_TEXT, HAFIF_TEXT, ORTA_TEXT, COK_TEXT)
    var BMR by remember { mutableStateOf<Double?>(null) }
    var totalCaloriesPerDay by remember { mutableStateOf<Double?>(null) }
    var vucutAgirligi by remember { mutableStateOf(75) }
    var boyUzunlugu by remember { mutableStateOf(175) }
    var yasDegeri by remember { mutableStateOf(20) }
    var showDialog by remember { mutableStateOf(false) }
    var showButton by remember { mutableStateOf(false) }


    if (!selectedCinsiyet.isNullOrEmpty()){
        when(selectedCinsiyet) {
            ERKEK -> { BMR = (10 * vucutAgirligi) + (6.25 * boyUzunlugu) - (5 * yasDegeri) + 5 }
            KADIN -> { BMR = (10 * vucutAgirligi) + (6.25 * boyUzunlugu) - (5 * yasDegeri) - 161 }
        }
    }
    if (!selectedOption.isNullOrEmpty() && !selectedCinsiyet.isNullOrEmpty()) {
        showButton = true
        when(selectedOption) {
            SEDANTER -> { totalCaloriesPerDay = BMR?.times(1.2) }
            HAFIF -> { totalCaloriesPerDay = BMR?.times(1.375) }
            ORTA -> { totalCaloriesPerDay = BMR?.times(1.55) }
            COK -> { totalCaloriesPerDay = BMR?.times(1.725) }
        }
    }
    if (showDialog) CustomCalculatorDialog(dialogState = showDialog,BMR!!,totalCaloriesPerDay!!) { showDialog = !showDialog }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(color = grey50), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.Cinsiyet),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        SelectableGenderGroup(
            options = cinsiyetSecim,
            selectedOption = selectedCinsiyet,
            onOptionSelected = { option ->
                selectedCinsiyet = option
            }
        )
        Text(
            text = stringResource(id = R.string.AktiviteSeviyesi),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        SelectableGroupWithGrid(
            modifier = Modifier.padding(16.dp),
            options = aktiviteSeviyesi,
            optionData = aktiviteSeviyesiDetay,
            selectedOption = selectedOption,
            onOptionSelected = { option ->
                selectedOption = option
            }
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Kilo", style = MaterialTheme.typography.labelMedium)
                NumberPicker(
                    textStyle = TextStyle(color = Color.White),
                    value = vucutAgirligi,
                    onValueChange = {
                        vucutAgirligi = it
                    }, range = 0..200,
                    dividersColor = MaterialTheme.colorScheme.primary
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Boy", style = MaterialTheme.typography.labelMedium)
                NumberPicker(
                    textStyle = TextStyle(color = Color.White),
                    value = boyUzunlugu,
                    onValueChange = {
                        boyUzunlugu = it
                    }, range = 140..220,
                    dividersColor = MaterialTheme.colorScheme.primary
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Yaş", style = MaterialTheme.typography.labelMedium)
                NumberPicker(
                    textStyle = TextStyle(color = Color.White),
                    value = yasDegeri,
                    onValueChange = {
                        yasDegeri = it
                    }, range = 14..80,
                    dividersColor = MaterialTheme.colorScheme.primary
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        AnimatedVisibility(
            visible = showButton,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(
                    durationMillis = 700,
                    easing = FastOutSlowInEasing
                )
            )
        ) {
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                onClick = { showDialog = !showDialog },
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.background,
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.Hesapla),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 40.dp)
                )
            }
        }

    }
}

@Composable
fun SelectableGenderGroup(
    modifier: Modifier = Modifier,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        options.forEachIndexed { index, option ->
            val selected = option == selectedOption
            SelectableItem(modifier = Modifier.weight(1f), selected = selected, title = option) {
                if (!selected) {
                    onOptionSelected(option)
                }
            }
        }
    }
}

@Composable
fun SelectableGroupWithGrid(
    modifier: Modifier = Modifier,
    options: List<String>,
    optionData: List<Any>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        val rows = options.chunked(2)
        rows.forEachIndexed { rowIndex, rowOptions ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                rowOptions.forEach { option ->
                    val selected = option == selectedOption
                    val index = options.indexOf(option)
                    val data = optionData.getOrNull(index)
                    SelectableItem(
                        selected = selected,
                        title = option,
                        subtitle = data?.toString() ?: "",
                        onClick = {
                            if (!selected) {
                                onOptionSelected(option)
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .heightIn(min = 0.dp)
                    )
                }
            }
        }
    }
}
@Composable
fun CustomCalculatorDialog(
    dialogState: Boolean,
    BMR: Double,
    totalCaloriesPerDay: Double,
    onDissmiss: () -> Unit

) {
    val showDialog = remember { mutableStateOf(dialogState) }
    if (showDialog.value) {
        Dialog(
            onDismissRequest = { onDissmiss.invoke() },
            properties = DialogProperties(dismissOnClickOutside = true, dismissOnBackPress = true)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = grey30
                )
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 12.dp, horizontal = 8.dp),
                    text = stringResource(id = R.string.KalorinizHesaplandi),
                    style = MaterialTheme.typography.titleSmall
                )
                Text(modifier = Modifier
                    .align(Alignment.Start)
                    .padding(vertical = 8.dp, horizontal = 16.dp),text = "Bazal Metabolizma Hızın: $BMR", style = MaterialTheme.typography.labelMedium,color = White40
                )
                Text(modifier = Modifier
                    .align(Alignment.Start)
                    .padding(vertical = 8.dp, horizontal = 16.dp),text = "Günlük Kalori İhtiyacın: $totalCaloriesPerDay", style = MaterialTheme.typography.labelMedium,color = White40
                )
                Text(modifier = Modifier
                    .align(Alignment.Start)
                    .padding(16.dp),text = stringResource(id = R.string.customDialogText), style = MaterialTheme.typography.labelMedium, color = white30
                )




                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                    onClick = { onDissmiss.invoke() },
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background,
                    ),
                ) {
                    Text(
                        text = stringResource(id = R.string.Kapat),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 40.dp)
                    )
                }


            }
        }
    }

}
@Composable
fun SelectableGroup(
    modifier: Modifier = Modifier,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit
) {
    Column(modifier = modifier.padding(horizontal = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        options.forEachIndexed { index, option ->
            val selected = option == selectedOption
            //val data = optionData.getOrNull(index)
            SelectableItem(
                selected = selected,
                title = option,
                //subtitle = data?.toString() ?: "",
                onClick = {
                    if (!selected) {
                        onOptionSelected(option)
                    }
                }
            )
        }
    }
}

@Composable
fun SelectableItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    title: String,
    titleColor: Color = if (selected) MaterialTheme.colorScheme.primary else White40,
    titleSize: TextStyle = MaterialTheme.typography.titleSmall,
    titleWeight: FontWeight = FontWeight.Normal,
    subtitle: String? = null,
    subtitleColor: Color = if (selected) MaterialTheme.colorScheme.primary else White40,
    borderWith: Dp = 1.dp,
    borderColor: Color = if (selected) MaterialTheme.colorScheme.primary else White40,
    borderShape: Shape = RoundedCornerShape(size = 10.dp),
    icon: ImageVector = Icons.Default.CheckCircle,
    iconColor: Color = if (selected) MaterialTheme.colorScheme.primary else White40,
    onClick: () -> Unit
) {
    val scaleA = remember { Animatable(initialValue = 1f) }
    val scaleB = remember { Animatable(initialValue = 1f) }

    val clickEnabled = remember { mutableStateOf(true) }

    LaunchedEffect(key1 = selected) {
        if (selected) {
            clickEnabled.value = false

            val jobA = launch {
                scaleA.animateTo(
                    targetValue = 0.3f,
                    animationSpec = tween(
                        durationMillis = 50
                    )
                )
                scaleA.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
            val jobB = launch {
                scaleB.animateTo(
                    targetValue = 0.9f,
                    animationSpec = tween(
                        durationMillis = 50
                    )
                )
                scaleB.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }

            jobA.join()
            jobB.join()
            clickEnabled.value = true
        }
    }

    Column(
        modifier = modifier
            .scale(scale = scaleB.value)
            .border(
                width = borderWith,
                color = borderColor,
                shape = borderShape
            )
            .clip(borderShape)
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier.padding(start = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(8f),
                text = title,
                style = titleSize,
                color = titleColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            IconButton(
                modifier = Modifier
                    .weight(2f)
                    .scale(scale = scaleA.value),
                onClick = {
                    onClick()
                }
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Selectable Item Icon",
                    tint = iconColor
                )
            }
        }
        if (subtitle != null) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 12.dp),
                text = subtitle,
                style = TextStyle(
                    color = subtitleColor
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}