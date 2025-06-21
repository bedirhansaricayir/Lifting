package com.lifting.app.feature.workout_editor.warmup_calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifting.app.core.common.extensions.toArrayList
import com.lifting.app.core.common.utils.generateUUID
import com.lifting.app.core.model.WarmUpSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 10.06.2025
 */

@HiltViewModel
class WarmUpCalculatorViewModel @Inject constructor() : ViewModel() {
    private var _sets = MutableStateFlow<List<WarmUpSet>>(emptyList())
    val sets = _sets.asStateFlow()

    fun setSets(newSets: List<WarmUpSet>) {
        viewModelScope.launch {
            _sets.value = newSets
        }
    }

    fun updateWorkSet(barWeight: Double, newWorkSet: Double) {
        viewModelScope.launch {
            val newSets = _sets.value.toArrayList()
            newSets.forEach {
                it.weight = if (it.weightPercentage == -1 || it.weightPercentage == null) {
                    barWeight
                } else {
                    newWorkSet * it.weightPercentage!! / 100
                }
            }
        }
    }

    fun updateSet(set: WarmUpSet) {
        viewModelScope.launch {
            val newSets = _sets.value.toArrayList()
            try {
                val index = newSets.indexOf(newSets.find { set.id == it.id })
                newSets[index] = set
                _sets.value = newSets
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addEmptySet(barWeight: Double) {
        viewModelScope.launch {
            val newSets = _sets.value.toArrayList()
            newSets.add(
                WarmUpSet(
                    id = generateUUID(),
                    formula = "Bar x 1",
                    reps = 1,
                    weight = barWeight,
                    weightPercentage = -1
                )
            )
            _sets.value = newSets
        }
    }

    fun deleteSet(set: WarmUpSet) {
        viewModelScope.launch {
            val newSets = _sets.value.toArrayList()
            val index = newSets.indexOf(newSets.find { set.id == it.id })
            newSets.removeAt(index)
            _sets.value = newSets
        }
    }
}