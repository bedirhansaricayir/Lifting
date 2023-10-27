package com.lifting.app.feature_tracker.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lifting.app.R
import com.lifting.app.feature_tracker.presentation.components.custom_fab.FabIcon
import com.lifting.app.feature_tracker.presentation.components.custom_fab.FabItemType
import com.lifting.app.feature_tracker.presentation.components.custom_fab.FabOption
import com.lifting.app.feature_tracker.presentation.components.custom_fab.MultiFabItem
import com.lifting.app.feature_tracker.presentation.components.custom_fab.MultiFabState
import com.lifting.app.feature_tracker.presentation.components.custom_fab.rememberMultiFabState

val MultiFabItemList = listOf(
    MultiFabItem(
        id = 1,
        iconRes = R.drawable.filter_icon,
        label = R.string.fab_filter_label,
        fabItemType = FabItemType.FILTER
    ),
    //TODO -> Feature eklenecek
    /*MultiFabItem(
        id = 2,
        iconRes = R.drawable.pulse,
        label = R.string.fab_filter_label,
        fabItemType = FabItemType.FILTER
    )*/
    MultiFabItem(
        id = 3,
        iconRes = R.drawable.add_chart,
        label = R.string.fab_insert_data,
        fabItemType = FabItemType.INSERT
    )
)

@ExperimentalAnimationApi
@Composable
fun MultiFloatingActionButton(
    modifier: Modifier = Modifier,
    items: List<MultiFabItem> = MultiFabItemList,
    fabState: MutableState<MultiFabState> = rememberMultiFabState(),
    fabIcon: FabIcon,
    fabOption: FabOption = FabOption(),
    onFabItemClicked: (fabItem: MultiFabItem) -> Unit,
    stateChanged: (fabState: MultiFabState) -> Unit = {}
) {
    val rotation by animateFloatAsState(
        if (fabState.value == MultiFabState.Expand) {
            fabIcon.iconRotate ?: 0f
        } else {
            0f
        }, label = ""
    )

    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.End
    ) {
        AnimatedVisibility(
            visible = fabState.value.isExpanded(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut()
        ) {
            LazyColumn(
                modifier = Modifier.wrapContentSize(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(items.size) { index ->
                    MiniFabItem(
                        item = items[index],
                        fabOption = fabOption,
                        onFabItemClicked = {
                            onFabItemClicked(it)
                            fabState.value = fabState.value.toggleValue()
                            stateChanged(fabState.value)
                        }
                    )
                }

                item {}
            }
        }

        FloatingActionButton(
            onClick = {
                fabState.value = fabState.value.toggleValue()
                stateChanged(fabState.value)
            },
            shape = CircleShape,
            containerColor = fabOption.backgroundTint,
            contentColor = fabOption.iconTint
        ) {
            Icon(
                painter = painterResource(id = fabIcon.iconRes),
                contentDescription = "FAB",
                modifier = Modifier.rotate(rotation),
                tint = fabOption.iconTint
            )
        }
    }
}

@Composable
fun MiniFabItem(
    item: MultiFabItem,
    fabOption: FabOption,
    onFabItemClicked: (item: MultiFabItem) -> Unit
) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(end = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (fabOption.showLabel) {
            Text(
                text = stringResource(id = item.label),
                style = MaterialTheme.typography.labelSmall,
                fontSize = 12.sp,
                color = fabOption.iconTint,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            )
        }

        FloatingActionButton(
            onClick = {
                onFabItemClicked(item)
            },
            modifier = Modifier.size(40.dp),
            containerColor = fabOption.backgroundTint,
            contentColor = fabOption.iconTint
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = item.iconRes),
                contentDescription = "Float Icon",
                tint = fabOption.iconTint
            )
        }
    }
}




