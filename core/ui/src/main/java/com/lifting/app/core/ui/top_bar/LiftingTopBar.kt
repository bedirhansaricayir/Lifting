package com.lifting.app.core.ui.top_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.R
import me.onebone.toolbar.CollapsingToolbarScope
import me.onebone.toolbar.CollapsingToolbarState

@Composable
fun LiftingTopBar(
    title: String = stringResource(id = R.string.top_bar_title_exercises),
    toolbarState: CollapsingToolbarState? = null,
    toolbarScope: CollapsingToolbarScope,
    statusBarEnabled: Boolean = true,
    navigationIcon: (@Composable BoxScope.() -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
    layout: (@Composable () -> Unit)? = null,
) {

    val liftingTheme = LiftingTheme.colors
    val localDensity = LocalDensity.current
    val statusBarTopHeight = WindowInsets.statusBars.getTop(localDensity)

    val titleScale = toolbarState?.let { (1f + (1.5f - 1f) * toolbarState.progress) } ?: 1f

    val toolbarHeight = 58.dp

    val statusBarHeight =
        with(localDensity) { if (statusBarEnabled) statusBarTopHeight.toDp() else 0.dp }

    val maxTitleOffset = 80
    val minTitleOffset = 24
    val titleOffset = if (navigationIcon != null && toolbarState != null) {
        (maxTitleOffset + (minTitleOffset - maxTitleOffset) * toolbarState.progress).dp
    } else {
        minTitleOffset.dp
    }

    var bottomLayoutHeight by rememberSaveable { mutableIntStateOf(0) }
    val bottomLayoutHeightDp = with(localDensity) { bottomLayoutHeight.toDp() }

    with(toolbarScope) {
        Box(
            modifier = Modifier
                .background(liftingTheme.background)
                .fillMaxWidth()
                .height(150.dp + statusBarHeight + bottomLayoutHeightDp)
                .pin()
        )


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(toolbarHeight + statusBarHeight + bottomLayoutHeightDp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(toolbarHeight + statusBarHeight)
                .align(Alignment.TopStart)
        ) {

            navigationIcon?.let {
                Box(
                    modifier = Modifier
                        .padding(top = statusBarHeight, start = 8.dp)
                        .align(Alignment.CenterStart)
                ) {
                    navigationIcon()
                }
            }

            actions?.let {
                Row(
                    modifier = Modifier
                        .padding(top = statusBarHeight, end = 8.dp)
                        .align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically,
                    content = actions
                )
            }
        }
    }

    Column(
        Modifier
            .defaultMinSize(minHeight = statusBarHeight + toolbarHeight + bottomLayoutHeightDp)
            .fillMaxWidth()
            .road(
                whenCollapsed = Alignment.BottomEnd,
                whenExpanded = Alignment.BottomEnd
            )
    ) {

        Box(
            modifier = Modifier
                .height(toolbarHeight + statusBarHeight)
                .offset(titleOffset, 0.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier
                    .padding(top = statusBarHeight)
                    .graphicsLayer(
                        scaleX = titleScale,
                        scaleY = titleScale,
                        transformOrigin = TransformOrigin(0f, 0.5f)
                    ),
                text = title,
                style = LiftingTheme.typography.header2,
                textAlign = TextAlign.Start,
                color = liftingTheme.onBackground,
                maxLines = 1
            )
        }

        layout?.let { layout ->
            Box(modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned {
                    bottomLayoutHeight = it.size.height
                }
            ) {
                layout()
            }
        }
    }
    }
}
