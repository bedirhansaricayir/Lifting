package com.lifting.app.core.ui.extensions.scroll_direction

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.lifting.app.core.model.ScrollDirection
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlin.math.abs

/**
 * Created by bedirhansaricayir on 08.05.2025
 */

@Composable
fun LazyGridState.observeScrollDirection(
    threshold: Int = 50,
    onScrollDirectionChanged: (ScrollDirection) -> Unit
) {
    var previousPosition by remember { mutableStateOf(position()) }
    var accumulatedDelta by remember { mutableIntStateOf(0) }
    var lastScrollDirection by remember { mutableStateOf<ScrollDirection?>(null) }

    LaunchedEffect(this) {
        snapshotFlow { position() }
            .distinctUntilChanged()
            .collect { currentPosition ->
                val delta = currentPosition.offsetFrom(averageItemHeight(), previousPosition)
                accumulatedDelta += delta

                if (abs(accumulatedDelta) >= threshold) {
                    val direction =
                        if (accumulatedDelta > 0) ScrollDirection.Down else ScrollDirection.Up

                    if (direction != lastScrollDirection) {
                        onScrollDirectionChanged(direction)
                        lastScrollDirection = direction
                    }
                    accumulatedDelta = 0
                }

                previousPosition = currentPosition
            }
    }
}

private data class GridScrollPosition(
    override val index: Int,
    override val offset: Int
) : ScrollPosition

private fun LazyGridState.position(): GridScrollPosition {
    return GridScrollPosition(firstVisibleItemIndex, firstVisibleItemScrollOffset)
}

private fun LazyGridState.averageItemHeight(): Int {
    val layoutInfo = layoutInfo
    val visibleItems = layoutInfo.visibleItemsInfo
    return if (visibleItems.isNotEmpty()) {
        visibleItems.sumOf { it.size.height } / visibleItems.size
    } else 0
}