package com.lifting.app.feature_calculators.presentation.tools_detail.bmi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.roundToInt

class BMIViewModel : ViewModel() {

    private val _state = MutableStateFlow(BMIScreenState())
    val state: StateFlow<BMIScreenState> = _state.asStateFlow()

    fun onEvent(event: BMIScreenEvent) {
        when (event) {
            BMIScreenEvent.OnGoButtonClicked -> calculateBMI()
            BMIScreenEvent.OnHeightValueClicked -> onValueClicked(false)
            BMIScreenEvent.OnWeightValueClicked -> onValueClicked(true)
            is BMIScreenEvent.OnNumberClicked -> {
                enterNumber(event.number)
            }

            BMIScreenEvent.OnAllClearButtonClicked -> allClearToZero()
            BMIScreenEvent.OnDeleteButtonClicked -> deleteLastDigit()
            BMIScreenEvent.OnWeightTextClicked -> sheetOpened(SheetTitleStage.WEIGHT)
            BMIScreenEvent.OnHeightTextClicked -> sheetOpened(SheetTitleStage.HEIGHT)
            is BMIScreenEvent.OnSheetItemClicked -> {
                changeWeightOrHeightUnit(event.sheetItem)
            }

            BMIScreenEvent.OnRefreshIconClicked -> refreshIconClicked()
            BMIScreenEvent.OnViewedErrorMessage -> setExistErrorState(false)
        }
    }

    private fun calculateBMI() {
        val weightInKgs: Double = when (_state.value.weightUnit) {
            "Pounds" -> _state.value.weightValue.toDouble().times(0.4536)
            else -> _state.value.weightValue.toDouble()
        }
        val heightInMeters: Double = when (_state.value.heightUnit) {
            "Centimeters" -> _state.value.heightValue.toDouble().times(0.01)
            "Feet" -> _state.value.heightValue.toDouble().times(0.3048)
            "Inches" -> _state.value.heightValue.toDouble().times(0.0254)
            else -> _state.value.heightValue.toDouble()
        }
        try {
            val bmiValue = weightInKgs / (heightInMeters * heightInMeters)
            val bmiValueWithDecimal = (bmiValue * 10).roundToInt() / 10.0
            val bmiStage = when (bmiValueWithDecimal) {
                in 0.0..18.5 -> BMIRangeState.UNDERWEIGHT
                in 18.5..25.0 -> BMIRangeState.NORMAL
                in 25.0..100.0 -> BMIRangeState.OVERWEIGHT
                else -> BMIRangeState.NONE
            }
            _state.value = _state.value.copy(
                shouldBMICardShow = true,
                bmi = if (bmiValueWithDecimal > 100) 0.0 else bmiValueWithDecimal,
                bmiStage = bmiStage
            )
        } catch (e: Exception) {
            setExistErrorState(true)
        }
    }

    private fun changeWeightOrHeightUnit(sheetItem: String) {
        if (_state.value.sheetTitle == SheetTitleStage.WEIGHT) {
            _state.value = _state.value.copy(weightUnit = sheetItem)
        } else if (_state.value.sheetTitle == SheetTitleStage.HEIGHT) {
            _state.value = _state.value.copy(heightUnit = sheetItem)
        }
    }

    private fun deleteLastDigit() {
        if (_state.value.weightValueStage != WeightValueStage.INACTIVE) {
            _state.value = _state.value.copy(
                weightValue = if (_state.value.weightValue.length == 1) "0"
                else _state.value.weightValue.dropLast(1)
            )
        } else if (_state.value.heightValueStage != HeightValueStage.INACTIVE) {
            _state.value = _state.value.copy(
                heightValue = if (_state.value.heightValue.length == 1) "0"
                else _state.value.heightValue.dropLast(1)
            )
        }
    }

    private fun allClearToZero() {
        if (_state.value.weightValueStage != WeightValueStage.INACTIVE) {
            _state.value = _state.value.copy(
                weightValue = "0",
                weightValueStage = WeightValueStage.ACTIVE
            )
        } else if (_state.value.heightValueStage != HeightValueStage.INACTIVE) {
            _state.value = _state.value.copy(
                heightValue = "0",
                heightValueStage = HeightValueStage.ACTIVE
            )
        }
    }

    private fun enterNumber(number: String) {
        when {
            _state.value.weightValueStage == WeightValueStage.ACTIVE -> {
                _state.value = _state.value.copy(
                    weightValue = if (number == ".") "0." else number,
                    weightValueStage = WeightValueStage.RUNNING
                )
            }

            _state.value.weightValueStage == WeightValueStage.RUNNING -> {
                if (_state.value.weightValue.contains(".").not()
                    && _state.value.weightValue.length <= 3
                ) {
                    if (_state.value.weightValue.length <= 2 && number != ".") {
                        _state.value = _state.value.copy(
                            weightValue = _state.value.weightValue + number,
                            weightValueStage = WeightValueStage.RUNNING
                        )
                    } else if (number == ".") {
                        _state.value = _state.value.copy(
                            weightValue = _state.value.weightValue + number,
                            weightValueStage = WeightValueStage.RUNNING
                        )
                    }
                } else if (
                    _state.value.weightValue.contains(".") &&
                    _state.value.weightValue.reversed().indexOf(".") < 2
                ) {
                    _state.value = _state.value.copy(
                        weightValue = _state.value.weightValue + number,
                        weightValueStage = WeightValueStage.RUNNING
                    )
                }

            }

            _state.value.heightValueStage == HeightValueStage.ACTIVE -> {
                _state.value = _state.value.copy(
                    heightValue = if (number == ".") "0." else number,
                    heightValueStage = HeightValueStage.RUNNING
                )
            }

            _state.value.heightValueStage == HeightValueStage.RUNNING -> {
                if (_state.value.heightValue.contains(".").not()
                    && _state.value.heightValue.length <= 3
                ) {
                    if (_state.value.heightValue.length <= 2 && number != ".") {
                        _state.value = _state.value.copy(
                            heightValue = _state.value.heightValue + number,
                            heightValueStage = HeightValueStage.RUNNING
                        )
                    } else if (number == ".") {
                        _state.value = _state.value.copy(
                            heightValue = _state.value.heightValue + number,
                            heightValueStage = HeightValueStage.RUNNING
                        )
                    }
                } else if (
                    _state.value.heightValue.contains(".") &&
                    _state.value.heightValue.reversed().indexOf(".") < 2
                ) {
                    _state.value = _state.value.copy(
                        heightValue = _state.value.heightValue + number,
                        heightValueStage = HeightValueStage.RUNNING
                    )
                }
            }
        }
    }

    private fun sheetOpened(sheetTitleStage: SheetTitleStage) {
        when (sheetTitleStage) {
            SheetTitleStage.WEIGHT -> _state.value = _state.value.copy(
                sheetTitle = sheetTitleStage,
                sheetItemsList = listOf("Kilograms", "Pounds")
            )

            SheetTitleStage.HEIGHT -> _state.value = _state.value.copy(
                sheetTitle = sheetTitleStage,
                sheetItemsList = listOf("Centimeter", "Meter", "Feet", "Inches")
            )
        }
    }

    private fun refreshIconClicked() {
        _state.value = _state.value.copy(
            shouldBMICardShow = false
        )
    }

    private fun setExistErrorState(state: Boolean) {
        _state.value = _state.value.copy(
            error = state
        )
    }

    private fun onValueClicked(isWeightValue: Boolean) {
        if (isWeightValue) {
            _state.value = _state.value.copy(
                weightValueStage = WeightValueStage.ACTIVE,
                heightValueStage = HeightValueStage.INACTIVE,
                shouldBMICardShow = false
            )
        }
        if (!isWeightValue) {
            _state.value = _state.value.copy(
                heightValueStage = HeightValueStage.ACTIVE,
                weightValueStage = WeightValueStage.INACTIVE,
                shouldBMICardShow = false
            )
        }
    }
}
