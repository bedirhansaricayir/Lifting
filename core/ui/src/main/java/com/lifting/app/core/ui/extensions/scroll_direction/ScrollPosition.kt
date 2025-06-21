package com.lifting.app.core.ui.extensions.scroll_direction

/**
 * Created by bedirhansaricayir on 10.05.2025
 */

internal interface ScrollPosition {
    val index: Int
    val offset: Int
}

internal fun ScrollPosition.offsetFrom(itemHeight: Int, other: ScrollPosition): Int {
    val indexDiff = this.index - other.index
    val offsetDiff = this.offset - other.offset
    return indexDiff * itemHeight + offsetDiff
}

