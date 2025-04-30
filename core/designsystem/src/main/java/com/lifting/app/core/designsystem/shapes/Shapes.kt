package com.lifting.app.core.designsystem.shapes

/**
 * Created by bedirhansaricayir on 14.07.2024
 */

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
class LiftingShapes {

    val small: CornerBasedShape
        @Composable
        get() = RoundedCornerShape(4.dp)

    val medium: CornerBasedShape
        @Composable
        get() = RoundedCornerShape(8.dp)

    val large: CornerBasedShape
        @Composable
        get() = RoundedCornerShape(12.dp)

    val xLarge: CornerBasedShape
        @Composable
        get() = RoundedCornerShape(16.dp)

    @Composable
    fun gridShapes(listSize: Int, index: Int): CornerBasedShape {
        val isLeft = index % 2 == 0
        val isRight = isLeft.not()
        val rowIndex = index / 2
        val rowCount = (listSize + 1) / 2
        val isTopRow = rowIndex == 0
        val isBottomRow = rowIndex == rowCount - 1
        val isSingleItem = listSize == 1

        return if (isSingleItem) RoundedCornerShape(8.dp)
        else RoundedCornerShape(
            topStart = if (isLeft && isTopRow) 8.dp else 0.dp,
            topEnd = if (isRight && isTopRow) 8.dp else 0.dp,
            bottomStart = if (isLeft && isBottomRow) 8.dp else 0.dp,
            bottomEnd = if (isRight && isBottomRow) 8.dp else 0.dp
        )
    }

    @Composable
    fun listShapes(listSize: Int,index: Int): CornerBasedShape {
        return if (listSize == 1) RoundedCornerShape(8.dp)
        else RoundedCornerShape(
            topStart = if (index == 0) 8.dp else 0.dp,
            topEnd = if (index == 0) 8.dp else 0.dp,
            bottomStart = if (index == listSize - 1) 8.dp else 0.dp,
            bottomEnd = if (index == listSize - 1) 8.dp else 0.dp
        )
    }
}

internal val LocalShapes = staticCompositionLocalOf { LiftingShapes() }