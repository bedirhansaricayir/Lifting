package com.lifting.app.feature_home.presentation.tracker.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lifting.app.R

enum class SortBy {
    DATE, RECORD
}
data class SortByModel(
    @StringRes val title: Int,
    val sortBy: SortBy,
    @DrawableRes val leadingIcon: Int,
    @DrawableRes val unselectedTrailingIcon: Int,
    @DrawableRes val selectedTrailingIcon: Int
    
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
            leadingIcon = R.drawable.baseline_date_range_24,
            unselectedTrailingIcon = R.drawable.outline_circle_24,
            selectedTrailingIcon = R.drawable.filled_check_circle_24
        ),
        SortByModel(
            title = R.string.sort_by_records,
            sortBy = SortBy.RECORD,
            leadingIcon = R.drawable.baseline_history_24,
            unselectedTrailingIcon = R.drawable.outline_circle_24,
            selectedTrailingIcon = R.drawable.filled_check_circle_24
        )
    )
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        options.forEachIndexed { index, sortByModel ->
            val selected = selectedSortBy == sortByModel.sortBy
            SortByItem(isSelected = selected, sortByModel = sortByModel) {
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
        .padding(vertical = 16.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically)
    {
        Icon(painter = painterResource(id = sortByModel.leadingIcon), contentDescription = "Leading Icon")
        Text(
            text = stringResource(id = sortByModel.title), modifier = Modifier
                .padding(8.dp)
                .weight(1f), style = MaterialTheme.typography.labelSmall
        )
        Icon(
            painter = painterResource(id = if (isSelected) sortByModel.selectedTrailingIcon else sortByModel.unselectedTrailingIcon),
            contentDescription = "Trailing Icon"
        )
    }
}