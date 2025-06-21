package com.lifting.app.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScaffoldScope
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.CollapsingToolbarScope
import me.onebone.toolbar.ScrollStrategy

/**
 * Created by bedirhansaricayir on 31.07.2024
 */

@Composable
fun CollapsingToolBarScaffold(
    modifier: Modifier = Modifier,
    state: CollapsingToolbarScaffoldState,
    scrollStrategy: ScrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
    toolbar: @Composable() (CollapsingToolbarScope.() -> Unit),
    body: @Composable() (CollapsingToolbarScaffoldScope.() -> Unit),
) {
    CollapsingToolbarScaffold(
        modifier = modifier,
        state = state,
        scrollStrategy = scrollStrategy,
        toolbar = toolbar,
        body = body
    )
}