package com.lifting.app.feature_tracker.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lifting.app.R

enum class FilterChip {
    VALUES,CIRCLE,FILLED
}

data class ChipsModel(
    @StringRes
    val name: Int,
    val chipEnum: FilterChip,
    val subList: List<String>? = null,
    val textExpanded: String? = null,
    val leadingIcon: ImageVector? = null,
    val trailingIcon: ImageVector? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersChip(selectedChipGroup: MutableList<FilterChip>, onChipSelected: (selectedItems: MutableList<FilterChip>) -> Unit) {
    val filterList = listOf(
        ChipsModel(
            name = R.string.values_visible,
            chipEnum = FilterChip.VALUES,
            leadingIcon = Icons.Default.Check,
        ),
        ChipsModel(
            name = R.string.circle_visible,
            chipEnum = FilterChip.CIRCLE,
            leadingIcon = Icons.Default.Check,
        ),
        ChipsModel(
            name = R.string.filled_visible,
            chipEnum = FilterChip.FILLED,
            leadingIcon = Icons.Default.Check,
        )
    )

    val selectedItems = remember { selectedChipGroup.toMutableStateList() }
    //val selectedItems2 = remember { mutableStateListOf<FilterChip>(FilterChip.VALUES) }
    var isSelected by remember { mutableStateOf(false) }
    LazyRow {
        items(filterList) { item ->
            isSelected = selectedItems.contains(item.chipEnum)
            Spacer(modifier = Modifier.padding(5.dp))
            FilterChip(
                selected = isSelected,
                onClick = {
                    when (selectedItems.contains(item.chipEnum)) {
                        true -> selectedItems.remove(item.chipEnum)
                        false -> selectedItems.add(item.chipEnum)
                    }
                    onChipSelected(selectedItems)

                },
                label = { Text(text = stringResource(id = item.name)) },
                leadingIcon = {
                    val isCheckIcon = item.leadingIcon == Icons.Default.Check
                    if (item.leadingIcon != null && isCheckIcon && isSelected) {
                        Icon(item.leadingIcon, contentDescription = "item.name")
                    }
                    if (item.leadingIcon != null && !isCheckIcon) {
                        Icon(item.leadingIcon, contentDescription = "item.name")
                    }
                },
                trailingIcon = {
                    if (item.trailingIcon != null && isSelected)
                        Icon(item.trailingIcon, contentDescription = "item.name")
                },
                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = MaterialTheme.colorScheme.primary)
            )

        }
    }
}