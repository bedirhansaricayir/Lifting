package com.lifting.app.feature.calendar.components

import androidx.compose.runtime.Immutable
import java.io.Serializable

/**
 * Created by bedirhansaricayir on 06.06.2025
 */

@Immutable
internal class VisibleItemState(
    val firstVisibleItemIndex: Int = 0,
    val firstVisibleItemScrollOffset: Int = 0,
) : Serializable