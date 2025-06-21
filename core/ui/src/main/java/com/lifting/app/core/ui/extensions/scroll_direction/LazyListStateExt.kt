package com.lifting.app.core.ui.extensions.scroll_direction

import androidx.compose.foundation.lazy.LazyListState
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
fun LazyListState.observeScrollDirection(
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

private data class ListScrollPosition(
    override val index: Int,
    override val offset: Int
): ScrollPosition

private fun LazyListState.position(): ListScrollPosition {
    return ListScrollPosition(firstVisibleItemIndex, firstVisibleItemScrollOffset)
}

private fun LazyListState.averageItemHeight(): Int {
    val layoutInfo = layoutInfo
    val visibleItems = layoutInfo.visibleItemsInfo
    return if (visibleItems.isNotEmpty()) {
        visibleItems.sumOf { it.size } / visibleItems.size
    } else 0
}