package com.lifting.app.feature_home.presentation.tracker.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lifting.app.R

enum class SortBy {
    DATE, RECORD
}
data class SortByModel(
    @StringRes val title: Int,
    val sortBy: SortBy,
    val leadingIcon: ImageVector,
    val trailingIcon: ImageVector,
    val selectedIcon: ImageVector
)

@Composable
fun SelectableSortBy(
    modifier: Modifier = Modifier,
    selectedSortBy: SortBy = SortBy.DATE,
    onShortBySelected: (SortBy) -> Unit
) {
    val options = listOf(
        SortByModel(
            title = R.string.sort_by_date,
            sortBy = SortBy.DATE,
            leadingIcon = Icons.Default.DateRange,
            trailingIcon = Icons.Outlined.CheckCircle,
            selectedIcon = Icons.Filled.CheckCircle
        ),
        SortByModel(
            title = R.string.sort_by_records,
            sortBy = SortBy.RECORD,
            leadingIcon = Icons.Default.DateRange,
            trailingIcon = Icons.Outlined.CheckCircle,
            selectedIcon = Icons.Filled.CheckCircle
        )
    )
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        options.forEachIndexed { index, sortByModel ->
            val selected = selectedSortBy == sortByModel.sortBy
            SortByItem(isSelected = selected,sortByModel = sortByModel) {
                onShortBySelected(sortByModel.sortBy)
            }
        }
    }
}

@Composable
fun SortByItem(
    isSelected: Boolean,
    sortByModel: SortByModel,
    onSortByClicked: () -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onSortByClicked()
        }
        .padding(vertical = 16.dp, horizontal = 8.dp), verticalAlignment = Alignment.CenterVertically)
    {
        Icon(imageVector = sortByModel.leadingIcon, contentDescription = "Leading Icon")
        Text(
            text = stringResource(id = sortByModel.title), modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        )
        Icon(
            imageVector = if (isSelected) sortByModel.selectedIcon else sortByModel.trailingIcon,
            contentDescription = "Trailing Icon"
        )
    }
}