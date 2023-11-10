package com.lifting.app.feature_calculators.presentation.tools_detail.bmr

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.chargemap.compose.numberpicker.NumberPicker
import com.lifting.app.R
import com.lifting.app.feature_home.presentation.components.CommonTopBar
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@Composable
fun BMRScreen(
    state: BMRScreenState,
    onEvent: (BMRScreenEvent) -> Unit,
    onBackNavigationIconClicked: () -> Unit
) {
    BMRScreenContent(
        state = state,
        onGenderSelected = { onEvent(BMRScreenEvent.OnGenderSelected(it)) },
        onActivityLevelSelected = { onEvent(BMRScreenEvent.OnActivityLevelSelected(it)) },
        onCalculateButtonClicked = { measurements ->
            onEvent(BMRScreenEvent.OnCalculateButtonClicked(measurements))
        },
        onBackNavigationIconClicked = onBackNavigationIconClicked
    )
}

@Composable
fun BMRScreenContent(
    state: BMRScreenState,
    onGenderSelected: (Gender) -> Unit,
    onActivityLevelSelected: (ActivityLevel) -> Unit,
    onCalculateButtonClicked: (measurements: BodyMeasurements) -> Unit,
    onBackNavigationIconClicked: () -> Unit
) {
    var weight by remember { mutableStateOf(75) }
    var height by remember { mutableStateOf(175) }
    var age by remember { mutableStateOf(20) }
    var showDialog by remember { mutableStateOf(false) }
    var showButton by remember { mutableStateOf(false) }

    var selectedGender by remember { mutableStateOf<Gender?>(null) }
    var selectedActivityLevel by remember { mutableStateOf<ActivityLevel?>(null) }

    if (selectedGender != null && selectedActivityLevel != null) {
        showButton = true
    }

    if (showDialog){
        CustomCalculatorDialog(
            dialogState = showDialog,
            bmr = state.bmr,
            totalCaloriesPerDay = state.dailyCalories,
            onDissmiss = { showDialog = !showDialog }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(color = MaterialTheme.colorScheme.background), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CommonTopBar(
            title = R.string.bmr_calculator_label,
            onBackNavigationIconClicked = onBackNavigationIconClicked
        )

        Text(
            text = stringResource(id = R.string.Cinsiyet),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        SelectableGenderGroup(
            selectedGender = selectedGender,
            onGenderSelected = {
                selectedGender = it
                onGenderSelected(it)
            }
        )

        Text(
            text = stringResource(id = R.string.AktiviteSeviyesi),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        SelectableActivityLevelGroup(
            selectedActivityLevel = selectedActivityLevel,
            onActivityLevelSelected = {
                selectedActivityLevel = it
                onActivityLevelSelected(it)
            }
        )

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(id = R.string.bmr_weight_label), style = MaterialTheme.typography.labelMedium)
                NumberPicker(
                    textStyle = TextStyle(color = Color.White),
                    value = weight,
                    onValueChange = { weight = it },
                    range = 0..200,
                    dividersColor = MaterialTheme.colorScheme.primary
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(id = R.string.bmr_height_label), style = MaterialTheme.typography.labelMedium)
                NumberPicker(
                    textStyle = TextStyle(color = Color.White),
                    value = height,
                    onValueChange = { height = it },
                    range = 140..220,
                    dividersColor = MaterialTheme.colorScheme.primary
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(id = R.string.bmr_age_label), style = MaterialTheme.typography.labelMedium)
                NumberPicker(
                    textStyle = TextStyle(color = Color.White),
                    value = age,
                    onValueChange = { age = it },
                    range = 14..80,
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
                onClick = {
                    showDialog = !showDialog
                    onCalculateButtonClicked(BodyMeasurements(weight, height, age))
                },
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
    selectedGender: Gender?,
    onGenderSelected: (Gender) -> Unit
) {
    val genderOptions = Gender.values()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        genderOptions.forEach { gender ->
            val selected = gender == selectedGender
            SelectableItem(
                modifier = Modifier.weight(1f),
                selected = selected,
                icon = if (selected) R.drawable.filled_check_circle_24 else R.drawable.outline_circle_24,
                title = stringResource(id = gender.gender)
            ) {
                if (!selected) {
                    onGenderSelected(gender)
                }
            }
        }
    }
}

@Composable
fun SelectableActivityLevelGroup(
    modifier: Modifier = Modifier,
    selectedActivityLevel: ActivityLevel?,
    onActivityLevelSelected: (ActivityLevel) -> Unit
) {
    val activityLevels = ActivityLevel.values().toList()
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val rows = activityLevels.chunked(2)
        rows.forEach { rowOptions ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowOptions.forEach { activityLevel ->
                    val selected = activityLevel == selectedActivityLevel
                    SelectableItem(
                        selected = selected,
                        icon = if (selected) R.drawable.filled_check_circle_24 else R.drawable.outline_circle_24,
                        title = stringResource(id = activityLevel.activityLevel),
                        subtitle = stringResource(id = activityLevel.activityLevelDesc),
                        onClick = {
                            if (!selected) {
                                onActivityLevelSelected(activityLevel)
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
    bmr: Double,
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
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 12.dp, horizontal = 8.dp),
                    text = stringResource(id = R.string.KalorinizHesaplandi),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    text = stringResource(id = R.string.your_bmr_label)+": $bmr",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    text = stringResource(id = R.string.your_daily_calories)+": ${(totalCaloriesPerDay * 10.0).roundToInt() / 10.0}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(16.dp),
                    text = stringResource(id = R.string.customDialogText),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface
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
                        text = stringResource(id = R.string.close_label),
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
    Column(
        modifier = modifier.padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        options.forEachIndexed { index, option ->
            val selected = option == selectedOption
            //val data = optionData.getOrNull(index)
            SelectableItem(
                selected = selected,
                icon = if (selected) R.drawable.filled_check_circle_24 else R.drawable.outline_circle_24,
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
    titleColor: Color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
    titleSize: TextStyle = MaterialTheme.typography.titleSmall,
    titleWeight: FontWeight = FontWeight.Normal,
    subtitle: String? = null,
    subtitleColor: Color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
    borderWith: Dp = 1.dp,
    borderColor: Color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
    borderShape: Shape = RoundedCornerShape(size = 10.dp),
    @DrawableRes icon: Int = R.drawable.filled_check_circle_24,
    iconColor: Color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
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
                    painter = painterResource(id = icon),
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