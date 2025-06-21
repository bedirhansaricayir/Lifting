package com.lifting.app.core.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lifting.app.core.designsystem.LiftingTheme

/**
 * Created by bedirhansaricayir on 04.03.2025
 */

fun Modifier.liftingTabIndicatorOffset(
    currentTabPosition: TabPosition
): Modifier = composed() {
    val currentTabWidth by animateDpAsState(
        targetValue = currentTabPosition.width,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.left,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
    )
    wrapContentSize(Alignment.CenterStart)
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
}

@Composable
fun LiftingTabIndicator(
    tabPosition: List<TabPosition>,
    pagerState: PagerState,
    indicatorColor: Color = Color.Black,
) {
    val transition = updateTransition(targetState = pagerState.currentPage)
    val indicatorStartWithAnimation by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 50f)
            } else {
                spring(dampingRatio = 1f, stiffness = 300f)
            }
        },
        label = ""
    ) {
        tabPosition[it].left
    }
    val indicatorEndWithAnimation by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 300f)
            } else {
                spring(dampingRatio = 1f, stiffness = 50f)
            }
        },
        label = ""
    ) {
        tabPosition[it].right
    }


    Box(
        Modifier
            .offset(x = indicatorStartWithAnimation)
            .wrapContentSize(align = Alignment.BottomStart)
            .width(indicatorEndWithAnimation - indicatorStartWithAnimation)
            .padding(2.dp)
            .height(2.dp)
            .background(indicatorColor)
    )
}

@Composable
fun LiftingTabIndicator2(
    modifier: Modifier = Modifier,
    height: Dp = 3.dp,
    width: Dp = 36.dp,
    color: Color = LiftingTheme.colors.primary,
    shape: Shape = RoundedCornerShape(3.dp,3.dp),
) {
    Spacer(
        modifier
            .requiredHeight(height)
            .requiredWidth(width)
            .background(color = color, shape = shape)
    )
}
