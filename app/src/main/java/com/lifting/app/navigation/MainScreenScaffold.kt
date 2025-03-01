package com.lifting.app.navigation

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Surface
import androidx.compose.material.SwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.lifting.app.core.designsystem.LiftingTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * Created by bedirhansaricayir on 02.03.2025
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreenScaffold(
    swipeableState: SwipeableState<Int>,
    onPanelTopHeightChange: (Int) -> Unit = {},
    bottomBar: @Composable () -> Unit,
    panelHidden: Boolean = false,
    panel: @Composable () -> Unit,
    panelTopCommon: @Composable () -> Unit,
    panelTopCollapsed: @Composable () -> Unit,
    panelTopExpanded: @Composable () -> Unit,
    mainBody: @Composable () -> Unit,
) {
    BoxWithConstraints(Modifier.fillMaxSize()) {

        val parentHeight = constraints.minHeight

        val coroutine = rememberCoroutineScope()

        var panelTopHeight by remember() {
            mutableStateOf(1)
        }

        var panelFullHeight by remember {
            mutableStateOf(parentHeight)
        }

        var bottomBarHeight by remember {
            mutableStateOf(0)
        }

        var statusBarHeight by remember {
            mutableStateOf(0)
        }

        onPanelTopHeightChange(panelTopHeight)

        var panelHiddenContentHeight = panelFullHeight - panelTopHeight
        val anchors = mapOf(
            (panelHiddenContentHeight - bottomBarHeight).toFloat() to 0,
            0f to 1
        ) // Maps anchor points (in px) to states

        val newOffsetY = swipeableState.offset.value.roundToInt()


        val outOf1 =
            1f - ((newOffsetY).toFloat() / (panelHiddenContentHeight - bottomBarHeight).toFloat()).coerceIn(
                0f,
                1f
            )

        val extraFullOffsetY by animateIntAsState(
            targetValue = if (panelHidden) panelTopHeight + bottomBarHeight else 0,
            animationSpec = tween(
                durationMillis = 300,
//            easing = LinearEasing
            )
        )

        LaunchedEffect(key1 = panelHidden) {
            if (panelHidden && swipeableState.currentValue != 0) {
                swipeableState.animateTo(0)
            }
        }


        Layout(
            modifier = Modifier.background(
                color = LiftingTheme.colors.background
            ),
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsTopHeight(WindowInsets.statusBars)
                        .background(LiftingTheme.colors.background)
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .background(
                            color = LiftingTheme.colors.background
                        )
                        .alpha((1f - outOf1).coerceIn(0.7f, 1f))
                ) {
                    bottomBar()
                }
                val cornerRadius = (12 - (12 * (1f - (2f - (outOf1 * 2)).coerceIn(0f, 1f))))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .swipeable(
                            state = swipeableState,
                            anchors = anchors,
                            thresholds = { _, _ ->
                                // Automatically toggle the state when user lifts the finger
                                // when drag is reached 0.1f FractionalThreshold
                                FractionalThreshold(0.1f)
                            },
                            resistance = null, // passing null so the panel doesn't go beyond the specified height
                            orientation = Orientation.Vertical
                        )
                        .nestedScroll(
                            // We are using NestedScrollConnection to make panel swipeable when
                            // user scrolls inside the panel
                            object : NestedScrollConnection {
                                override fun onPreScroll(
                                    available: Offset,
                                    source: NestedScrollSource
                                ): Offset {
                                    val delta = available.y
                                    return if (delta < 0 && source == NestedScrollSource.Drag) {
                                        // User is moving the finger upwards. If the gesture goes in that direction,
                                        // we’re scrolling either the draggable composable or the scrollable inner content.
                                        Offset(0f, swipeableState.performDrag(delta))
                                    } else {
                                        // User is scrolling down. We can ignore this and pass all
                                        // the consumable space down to the child
                                        Offset.Zero
                                    }
                                }

                                override fun onPostScroll(
                                    consumed: Offset,
                                    available: Offset,
                                    source: NestedScrollSource
                                ): Offset {
                                    // if the list has finished scrolling, we will pass all the leftover space
                                    // to performDrag that will drag if necessary.
                                    return if (source == NestedScrollSource.Drag) {
                                        val delta = available.y
                                        Offset(0f, swipeableState.performDrag(delta))
                                    } else {
                                        Offset.Zero
                                    }
                                }

                                // Same as preScroll but this time we handle the fling
                                override suspend fun onPreFling(available: Velocity): Velocity {
                                    return if (available.y < 0 && swipeableState.currentValue == 0 && swipeableState.offset.value > Float.NEGATIVE_INFINITY) {
                                        swipeableState.performFling(available.y)
                                        available
                                    } else {
                                        Velocity.Zero
                                    }
                                }

                                // Same as postScroll but this time we handle the fling
                                override suspend fun onPostFling(
                                    consumed: Velocity,
                                    available: Velocity
                                ): Velocity {
                                    swipeableState.performFling(velocity = available.y)
                                    return super.onPostFling(consumed, available)
                                }
                            }),
                    elevation = 8.dp,
                    color = LiftingTheme.colors.background,
                    shape = RoundedCornerShape(
                        topStart = cornerRadius.dp,
                        topEnd = cornerRadius.dp
                    )
                ) {

                    Column(
                        Modifier
                            .fillMaxWidth()
                    ) {


                        Box(
                            modifier = Modifier
                                .onGloballyPositioned { constraints ->
                                    panelTopHeight =
                                        constraints.size.height
                                }
                                .clickable(
                                    indication = null, // passing null in indication so there won't be any ripple effect
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    coroutine.launch {
                                        swipeableState.animateTo(if (swipeableState.currentValue == 0) 1 else 0)
                                    }

                                }
                                .background(
                                    color = LiftingTheme.colors.background
                                ),
                        ) {

                            // Using additional Box so we can set alpha without recomposing the panelTopExpanded
                            (1f - (2f - (outOf1 * 2)).coerceIn(0f, 1f)).let { alpha ->
//                            if (alpha > 0f) {
                                Box(modifier = Modifier.alpha(alpha)) {
                                    panelTopExpanded()
                                }
//                            }
                            }

                            // Using additional Box so we can set alpha without recomposing the panelTopCollapsed
                            (1f - (outOf1 * 2).coerceIn(0f, 1f)).let { alpha ->
//                            if (alpha > 0f) {
                                Box(modifier = Modifier.alpha(alpha)) {
                                    panelTopCollapsed()
                                }
//                            }
                            }

                            // This panelTopCommon is always visible regard less of panel state
                            panelTopCommon()

                        }

                        // Main panel contents
                        Box(modifier = Modifier.fillMaxSize()) {
                            panel()
                        }
                    }
                }
                Box() {
                    mainBody()
                }

                Box() {
                    if (outOf1 > 0f) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .alpha(outOf1)
                                .background(color = Color.Black.copy(alpha = 0.32f))
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onTap = {
                                            coroutine.launch {
                                                swipeableState.animateTo(0)
                                            }
                                        }
                                    )
                                },
                        )
                    }
                }
            }) { measurables, constraints ->

            // ------------------ StatusBar STARTS
            val statusBarConstraints = constraints.copy(
                minWidth = 0,
                minHeight = 0,
            )
            val statusBarPlaceables = measurables[0].measure(statusBarConstraints)

            statusBarHeight = statusBarPlaceables.height
            // ------------------ StatusBar ENDS

            // ------------------ BottomBar STARTS
            val bottomBarConstraints = constraints.copy(
                minWidth = 0,
                minHeight = 0,
            )
            val bottomBarPlaceables = measurables[1].measure(bottomBarConstraints)

            bottomBarHeight = bottomBarPlaceables.height
            // ------------------ BottomBar ENDS

            // ------------------ Panel STARTS
            val panelConstraints = constraints.copy(
                minWidth = 0,
                minHeight = 0,
                maxHeight = constraints.maxHeight - statusBarHeight
            )
            val panelPlaceables = measurables[2].measure(panelConstraints)


            panelFullHeight = panelPlaceables.height

            // ------------------ Panel ENDS

            val bodyConstraints = constraints.copy(
                minWidth = 0,
                minHeight = 0,
                maxHeight = constraints.maxHeight - bottomBarHeight - (panelTopHeight - (extraFullOffsetY).coerceIn(
                    0,
                    panelTopHeight
                ))
//                maxHeight = constraints.maxHeight - bottomBarHeight - if (panelHidden) 0 else panelTopHeight
//                maxHeight = constraints.maxHeight - bottomBarHeight - panelTopHeight
//                maxHeight = constraints.maxHeight - bottomBarHeight - (extraFullOffsetY - panelTopHeight).coerceIn(0, panelFullHeight)
            )

            val bodyPlaceables = measurables[3].measure(bodyConstraints)


            val panelScrimConstraints = constraints.copy(
                minWidth = 0,
                minHeight = 0,
                maxHeight = constraints.maxHeight - bottomBarHeight
            )

            val panelScrimPlaceables = measurables[4].measure(panelScrimConstraints)


            val width = constraints.maxWidth
            val height = constraints.maxHeight

            layout(width, height) {
                bodyPlaceables.place(
                    0,
                    0
                )


                panelScrimPlaceables.place(
                    0,
                    0
                )



                panelPlaceables.place(
                    0,
//                    if (panelHidden) {
//                        height
//                    } else {
                    height - (panelFullHeight - newOffsetY) + extraFullOffsetY
//                    }

                )



                bottomBarPlaceables.place(
                    0,
                    height - (bottomBarHeight - bottomBarHeight * outOf1).roundToInt()

                )


                statusBarPlaceables.place(
                    0,
                    -(statusBarHeight - statusBarHeight * (1f - (2f - (outOf1 * 2)).coerceIn(
                        0f,
                        1f
                    ))).roundToInt()
                )


            }
        }
    }
}
