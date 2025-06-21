package com.lifting.app.core.model

/**
 * Created by bedirhansaricayir on 09.05.2025
 */

enum class LayoutType {
    List, Grid
}

fun LayoutType.opposite(): LayoutType {
    return when (this) {
        LayoutType.List -> LayoutType.Grid
        LayoutType.Grid -> LayoutType.List
    }
}

fun LayoutType.isList(): Boolean = this == LayoutType.List

fun LayoutType.isGrid(): Boolean = this == LayoutType.Grid