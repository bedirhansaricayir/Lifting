package com.lifting.app.feature.workout_editor.warmup_calculator

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.lifting.app.core.common.extensions.toKgFromLbs
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.keyboard.LiftingKeyboardHost
import com.lifting.app.core.keyboard.model.LiftingKeyboardType
import com.lifting.app.core.model.Barbell
import com.lifting.app.core.model.WarmUpSet
import com.lifting.app.core.model.WeightUnit
import com.lifting.app.core.ui.R
import com.lifting.app.core.ui.common.LocalAppSettings
import com.lifting.app.core.ui.common.toWeightUnit
import com.lifting.app.core.ui.common.toWeightUnitPreferencesString
import com.lifting.app.core.ui.components.LiftingButton
import com.lifting.app.core.ui.components.LiftingButtonDefaults
import com.lifting.app.core.ui.components.LiftingButtonType
import com.lifting.app.core.ui.components.LiftingSwipeToDismissBox
import com.lifting.app.feature.workout_editor.components.LiftingSetInputField

/**
 * Created by bedirhansaricayir on 10.06.2025
 */

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WarmUpCalculatorDialog(
    startingWorkSetWeight: Double?,
    barbell: Barbell?,
    startingSets: List<WarmUpSet>,
    onInsert: (workSet: Double, sets: List<WarmUpSet>) -> Unit,
    onDismissRequest: () -> Unit,
    viewModel: WarmUpCalculatorViewModel = hiltViewModel()
) {
    val sets by viewModel.sets.collectAsState()

    LaunchedEffect(startingSets) {
        viewModel.setSets(startingSets)
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        content = {
            LiftingKeyboardHost {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    WarmUpCalculatorDialogLayout(
                        sets = sets,
                        barbell = barbell,
                        startingWorkSetWeight = startingWorkSetWeight,
                        onClickCancel = onDismissRequest,
                        onUpdateWorkSet = { barWeight, workSet ->
                            viewModel.updateWorkSet(barWeight, workSet)
                        },
                        onUpdateSet = {
                            viewModel.updateSet(it)
                        },
                        onAddSet = {
                            viewModel.addEmptySet(it)
                        },
                        onClickInsert = {
                            onInsert(it, sets)
                            onDismissRequest()
                        },
                        onDeleteSet = {
                            viewModel.deleteSet(it)
                        }
                    )
                }
            }
        }
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun WarmUpCalculatorDialogLayout(
    sets: List<WarmUpSet>,
    barbell: Barbell?,
    startingWorkSetWeight: Double?,
    onUpdateWorkSet: (barWight: Double, workSet: Double) -> Unit,
    onUpdateSet: (WarmUpSet) -> Unit,
    onDeleteSet: (WarmUpSet) -> Unit,
    onAddSet: (barWight: Double) -> Unit,
    onClickInsert: (workSet: Double) -> Unit,
    onClickCancel: () -> Unit
) {
    val fieldValue = startingWorkSetWeight?.toWeightUnitPreferencesString().orEmpty()

    var workSetStr by remember {
        mutableStateOf(fieldValue)
    }

    val weightUnit = LocalAppSettings.current.weightUnit

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        color = LiftingTheme.colors.background,
        shape = LiftingTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier =
                    Modifier.padding(
                        start = LiftingTheme.dimensions.large,
                        end = LiftingTheme.dimensions.large,
                        top = LiftingTheme.dimensions.large,
                        bottom = LiftingTheme.dimensions.small
                    ),
                verticalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.medium)
            ) {
                Text(
                    text = stringResource(R.string.warm_up_sets),
                    style = LiftingTheme.typography.header2,
                    color = LiftingTheme.colors.onBackground
                )


                Text(
                    text = when (weightUnit) {
                        WeightUnit.Kg -> stringResource(R.string.work_set_kg)
                        WeightUnit.Lbs -> stringResource(R.string.work_set_lbs)
                    },
                    style = LiftingTheme.typography.caption,
                    color = LiftingTheme.colors.onBackground.copy(0.75f)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = LiftingTheme.dimensions.small)
            ) {
                LiftingSetInputField(
                    value = workSetStr,
                    onValueChange = {
                        workSetStr = it
                        onUpdateWorkSet(
                            barbell.toWeightUnit(weightUnit),
                            when (weightUnit) {
                                WeightUnit.Kg -> it.toDoubleOrNull()
                                WeightUnit.Lbs -> it.toDoubleOrNull()?.toKgFromLbs()
                            } ?: 0.0
                        )
                    },
                    keyboardType = LiftingKeyboardType.Weight()
                )
            }

            Spacer(modifier = Modifier.height(LiftingTheme.dimensions.large))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentPadding = PaddingValues(top = LiftingTheme.dimensions.small)
            ) {

                item(key = "warm_up_titles") {
                    WarmUpSetsTitlesComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItem()
                    )
                }

                items(items = sets, key = { it.id }) { set ->
                    WarmUpSetComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItem(),
                        workSetWeightKg = when (weightUnit) {
                            WeightUnit.Kg -> workSetStr.toDoubleOrNull()
                            WeightUnit.Lbs -> workSetStr.toDoubleOrNull()?.toKgFromLbs()
                        } ?: 0.0,
                        barWeightKg = barbell.toWeightUnit(WeightUnit.Kg),
                        startingSet = set,
                        onChangeValue = onUpdateSet,
                        onDeleteSet = {
                            onDeleteSet(set)
                        }
                    )

                }

                item(key = "warm_up_add_set_button") {

                    LiftingButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = LiftingTheme.dimensions.large)
                            .animateItem(),
                        buttonType = LiftingButtonType.PrimaryButton(
                            text = stringResource(R.string.add_set),
                            leadingIcon = LiftingTheme.icons.add
                        ),
                        colors = LiftingButtonDefaults.primaryButtonColors(
                            containerColor = LiftingTheme.colors.surface,
                            contentColor = LiftingTheme.colors.onBackground,
                        ),
                        onClick = { onAddSet(barbell.toWeightUnit(WeightUnit.Kg)) }
                    )
                }
            }

            DialogButtonsRow(
                onClickInsert = {
                    onClickInsert(workSetStr.toDoubleOrNull() ?: 0.0)
                },
                onClickCancel = onClickCancel
            )
        }
    }
}

@Composable
private fun ColumnScope.DialogButtonsRow(
    onClickInsert: () -> Unit,
    onClickCancel: () -> Unit
) {
    Row(
        modifier = Modifier
            .align(Alignment.End)
            .padding(
                start = LiftingTheme.dimensions.large,
                end = LiftingTheme.dimensions.large,
                bottom = 6.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.large)
    ) {
        LiftingButton(
            buttonType = LiftingButtonType.TextButton(
                text = stringResource(R.string.cancel),
            ),
            onClick = onClickCancel
        )

        LiftingButton(
            buttonType = LiftingButtonType.TextButton(
                text = stringResource(R.string.insert),
            ),
            onClick = onClickInsert
        )
    }
}

@Composable
private fun WarmUpSetsTitlesComponent(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(R.string.set),
            style = LiftingTheme.typography.caption,
            color = LiftingTheme.colors.onBackground.copy(alpha = 0.5f),
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(0.5f)
        )
        Text(
            text = stringResource(R.string.formula),
            style = LiftingTheme.typography.caption,
            color = LiftingTheme.colors.onBackground.copy(alpha = 0.5f),
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1.25f)
        )
        Text(
            text = stringResource(R.string.warm_up),
            style = LiftingTheme.typography.caption,
            color = LiftingTheme.colors.onBackground.copy(alpha = 0.5f),
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1.25f)
        )
    }
}

@Composable
internal fun WarmUpSetComponent(
    modifier: Modifier = Modifier,
    workSetWeightKg: Double,
    barWeightKg: Double,
    startingSet: WarmUpSet,
    onDeleteSet: () -> Unit,
    onChangeValue: (WarmUpSet) -> Unit
) {
    val setWeight =
        if (startingSet.weightPercentage == -1 || startingSet.weightPercentage == null) {
            barWeightKg
        } else {
            workSetWeightKg * startingSet.weightPercentage!! / 100
        }

    LiftingSwipeToDismissBox(
        modifier = modifier,
        containerColor = LiftingTheme.colors.background,
        onSwipeDelete = {
            onDeleteSet()
        }
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = LiftingTheme.colors.background
                )
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .height(32.dp)
                    .padding(horizontal = LiftingTheme.dimensions.large)
                    .weight(0.5f)
                    .clip(LiftingTheme.shapes.large)
                    .background(
                        color = LiftingTheme.colors.surface,
                        shape = LiftingTheme.shapes.large
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "W",
                    style = LiftingTheme.typography.caption,
                    color = Color.Yellow,
                    textAlign = TextAlign.Center,
                )
            }

            LiftingSetInputField(
                value = startingSet.findFormula(),
                keyboardType = LiftingKeyboardType.WarmupSet,
                onValueChange = {
                    val arr = it.split(" x ")
                    val weightInt =
                        getAllWarmUpWeights().find { w -> w.second == arr.getOrNull(0) }?.first
                            ?: -1

                    val weight = if (weightInt == -1) {
                        barWeightKg
                    } else {
                        workSetWeightKg * weightInt / 100
                    }

                    val reps = arr.getOrNull(1)?.toIntOrNull() ?: 1

                    onChangeValue(
                        startingSet.copy(
                            weight = weight,
                            reps = reps,
                            weightPercentage = weightInt,
                            formula = it
                        )
                    )
                }
            )
            Text(
                text = "${setWeight.toWeightUnitPreferencesString(addUnitSuffix = true)} x ${startingSet.reps ?: 0}",
                style = LiftingTheme.typography.caption,
                color = LiftingTheme.colors.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1.25f)
            )
        }
    }
}

private fun getAllWarmUpWeights(): List<Pair<Int, String>> {
    return buildList<Pair<Int, String>> {
        add(Pair(-1, "Bar"))

        for (i in 5..100) {
            if ((i % 5) == 0) {
                add(Pair(i, "$i%"))
            }
        }
    }
}