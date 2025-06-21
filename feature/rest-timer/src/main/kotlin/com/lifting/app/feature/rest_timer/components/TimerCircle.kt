package com.lifting.app.feature.rest_timer.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.service.TimerState
import com.lifting.app.core.service.isRunning
import com.lifting.app.core.ui.components.LiftingButton
import com.lifting.app.core.ui.components.LiftingButtonType
import kotlin.math.max
import kotlin.math.min

/**
 * Created by bedirhansaricayir on 11.06.2025
 */

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun TimerCircle(
    screenWidthDp: Int,
    screenHeightDp: Int,
    time: String,
    timerState: TimerState,
    elapsedTime: Long,
    totalTime: Long,
    onClickCancel: () -> Unit,
    onClickPause: () -> Unit,
    onClickResume: () -> Unit,
    onClickStart: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val maxRadius by remember { mutableIntStateOf(min(screenHeightDp, screenWidthDp)) }

    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = Modifier
            .size(maxRadius.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = modifier
                .size(maxRadius.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(targetState = timerState.isRunning()) { bln ->
                if (bln) {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            key(time) {
                                Text(
                                    modifier = Modifier,
                                    text = time,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Light,
                                        fontSize = 60.sp,
                                        letterSpacing = (-0.5).sp
                                    ),
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .padding(bottom = 28.dp)
                                .align(Alignment.BottomCenter)
                        ) {
                            if (timerState == TimerState.RUNNING) {
                                LiftingButton(
                                    buttonType = LiftingButtonType.IconButton(
                                        icon = LiftingTheme.icons.pause,
                                        tint = Color.Unspecified
                                    ),
                                    onClick = onClickPause
                                )

                            } else if (timerState == TimerState.PAUSED) {
                                LiftingButton(
                                    buttonType = LiftingButtonType.IconButton(
                                        icon = LiftingTheme.icons.play,
                                        tint = Color.Unspecified
                                    ),
                                    onClick = onClickResume
                                )
                            }
                            Spacer(Modifier.height(LiftingTheme.dimensions.large))
                            LiftingButton(
                                buttonType = LiftingButtonType.IconButton(
                                    icon = LiftingTheme.icons.close,
                                    tint = Color.Unspecified
                                ),
                                onClick = onClickCancel
                            )
                        }
                    }

                } else {
                    TimesList(
                        contentPadding = PaddingValues(vertical = with(density) { (this@BoxWithConstraints.constraints.minHeight / 3).toDp() }),
                        onClickStart = onClickStart
                    )
                }
            }
        }


        TimerCircle(
            modifier = modifier.align(Alignment.Center),
            elapsedTime = elapsedTime,
            totalTime = totalTime,
            cWidth = with(density) { constraints.minWidth.toDp() },
            cHeight = with(density) { constraints.minHeight.toDp() }
        )
    }

}

@Composable
private fun TimerCircle(
    modifier: Modifier = Modifier,
    elapsedTime: Long,
    cHeight: Dp,
    cWidth: Dp,
    totalTime: Long
) {
    val completedColor = LiftingTheme.colors.primary
    val remainderColor = completedColor.copy(alpha = 0.25f)
    val whitePercent by animateFloatAsState(
        targetValue =
//        min(
//            1f,
            1f - (elapsedTime.toFloat() / totalTime.toFloat())
//        ),
    )

    Canvas(modifier = modifier.size(cWidth, cHeight), onDraw = {
        val height = size.height
        val width = size.width
        val dotDiameter = 12.dp
        val strokeSize = 20.dp
        val radiusOffset = calculateRadiusOffset(
            strokeSize.value,
            dotDiameter.value,
            0f
        )

        val xCenter = width / 2f
        val yCenter = height / 2f
        val radius = min(xCenter, yCenter)
        val arcWidthHeight = ((radius - radiusOffset) * 2f)
        val arcSize = Size(arcWidthHeight, arcWidthHeight)

        val greenPercent = 1 - whitePercent

        drawArc(
            completedColor,
            270f,
            -greenPercent * 360f,
            false,
            topLeft = Offset(radiusOffset, radiusOffset),
            size = arcSize,
            style = Stroke(width = strokeSize.value)
        )

        drawArc(
            remainderColor,
            270f,
            whitePercent * 360,
            false,
            topLeft = Offset(radiusOffset, radiusOffset),
            size = arcSize,
            style = Stroke(width = strokeSize.value)
        )

    })
}

private fun calculateRadiusOffset(strokeSize: Float, dotStrokeSize: Float, markerStrokeSize: Float): Float {
    return max(strokeSize, max(dotStrokeSize, markerStrokeSize))
}
