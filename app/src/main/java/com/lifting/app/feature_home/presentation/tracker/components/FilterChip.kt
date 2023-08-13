package com.lifting.app.feature_home.presentation.tracker.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp



data class ChipsModel(
    val name: String,
    val subList: List<String>? = null,
    val textExpanded: String? = null,
    val leadingIcon: ImageVector? = null,
    val trailingIcon: ImageVector? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersChip(onChipSelected: (selectedItems: MutableList<String>) -> Unit) {
    val filterList = listOf(
        ChipsModel(
            name = "Data 1",
            leadingIcon = Icons.Default.Check,
        ),
        ChipsModel(
            name = "Data 2",
            leadingIcon = Icons.Default.Check,
        ),
        ChipsModel(
            name = "Data 3",
            leadingIcon = Icons.Default.Check,
        ),
        ChipsModel(
            name = "Data 4",
            leadingIcon = Icons.Default.Check,
        ),
    )
    val selectedItems = remember { mutableStateListOf<String>() }
    var isSelected by remember { mutableStateOf(false) }
    LazyRow {
        items(filterList) { item ->
            isSelected = selectedItems.contains(item.name)
            Spacer(modifier = Modifier.padding(5.dp))
            FilterChip(
                selected = isSelected,
                onClick = {
                    when (selectedItems.contains(item.name)) {
                        true -> selectedItems.remove(item.name)
                        false -> selectedItems.add(item.name)
                    }
                    onChipSelected(selectedItems)

                },
                label = { Text(text = item.name) },
                leadingIcon = {
                    val isCheckIcon = item.leadingIcon == Icons.Default.Check
                    if (item.leadingIcon != null && isCheckIcon && isSelected) {
                        Icon(item.leadingIcon, contentDescription = item.name)
                    }
                    if (item.leadingIcon != null && !isCheckIcon) {
                        Icon(item.leadingIcon, contentDescription = item.name)
                    }
                },
                trailingIcon = {
                    if (item.trailingIcon != null && isSelected)
                        Icon(item.trailingIcon, contentDescription = item.name)
                }
            )

        }
    }
}