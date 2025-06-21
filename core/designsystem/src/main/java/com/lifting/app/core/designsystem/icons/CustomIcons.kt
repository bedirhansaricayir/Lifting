package com.lifting.app.core.designsystem.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

/**
 * Created by bedirhansaricayir on 16.05.2025
 */

internal val Icons.Outlined.Dumbbell: ImageVector
    get() {
        if (_dumbbell != null) {
            return _dumbbell!!
        }
        _dumbbell = materialIcon(name = "Outlined.Dumbbell") {
            materialPath {
                moveTo(20.57f, 14.86f)
                lineTo(22.0f, 13.43f)
                lineTo(20.57f, 12.0f)
                lineTo(17.0f, 15.57f)
                lineTo(8.43f, 7.0f)
                lineTo(12.0f, 3.43f)
                lineTo(10.57f, 2.0f)
                lineTo(9.14f, 3.43f)
                lineTo(7.71f, 2.0f)
                lineTo(5.57f, 4.14f)
                lineTo(4.14f, 2.71f)
                lineTo(2.71f, 4.14f)
                lineToRelative(1.43f, 1.43f)
                lineTo(2.0f, 7.71f)
                lineToRelative(1.43f, 1.43f)
                lineTo(2.0f, 10.57f)
                lineTo(3.43f, 12.0f)
                lineTo(7.0f, 8.43f)
                lineTo(15.57f, 17.0f)
                lineTo(12.0f, 20.57f)
                lineTo(13.43f, 22.0f)
                lineToRelative(1.43f, -1.43f)
                lineTo(16.29f, 22.0f)
                lineToRelative(2.14f, -2.14f)
                lineToRelative(1.43f, 1.43f)
                lineToRelative(1.43f, -1.43f)
                lineToRelative(-1.43f, -1.43f)
                lineTo(22.0f, 16.29f)
                lineToRelative(-1.43f, -1.43f)
                close()
            }
        }
        return _dumbbell!!
    }

private var _dumbbell: ImageVector? = null

internal val Icons.Outlined.Run: ImageVector
    get() {
        if (_run != null) {
            return _run!!
        }
        _run = materialIcon(name = "Outlined.Run") {
            materialPath {
                moveTo(13.49f, 5.48f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                reflectiveCurveToRelative(-2.0f, 0.9f, -2.0f, 2.0f)
                reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                close()
                moveTo(9.89f, 19.38f)
                lineToRelative(1.0f, -4.4f)
                lineToRelative(2.1f, 2.0f)
                verticalLineToRelative(6.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(-7.5f)
                lineToRelative(-2.1f, -2.0f)
                lineToRelative(0.6f, -3.0f)
                curveToRelative(1.3f, 1.5f, 3.3f, 2.5f, 5.5f, 2.5f)
                verticalLineToRelative(-2.0f)
                curveToRelative(-1.9f, 0.0f, -3.5f, -1.0f, -4.3f, -2.4f)
                lineToRelative(-1.0f, -1.6f)
                curveToRelative(-0.4f, -0.6f, -1.0f, -1.0f, -1.7f, -1.0f)
                curveToRelative(-0.3f, 0.0f, -0.5f, 0.1f, -0.8f, 0.1f)
                lineToRelative(-5.2f, 2.2f)
                verticalLineToRelative(4.7f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(-3.4f)
                lineToRelative(1.8f, -0.7f)
                lineToRelative(-1.6f, 8.1f)
                lineToRelative(-4.9f, -1.0f)
                lineToRelative(-0.4f, 2.0f)
                lineToRelative(7.0f, 1.4f)
                close()
            }
        }
        return _run!!
    }

private var _run: ImageVector? = null

internal val Icons.Outlined.Folder: ImageVector
    get() {
        if (_folder != null) {
            return _folder!!
        }
        _folder = materialIcon(name = "Outlined.Folder") {
            materialPath {
                moveTo(9.17f, 6.0f)
                lineToRelative(2.0f, 2.0f)
                horizontalLineTo(20.0f)
                verticalLineToRelative(10.0f)
                horizontalLineTo(4.0f)
                verticalLineTo(6.0f)
                horizontalLineToRelative(5.17f)
                moveTo(10.0f, 4.0f)
                horizontalLineTo(4.0f)
                curveToRelative(-1.1f, 0.0f, -1.99f, 0.9f, -1.99f, 2.0f)
                lineTo(2.0f, 18.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(16.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineTo(8.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                horizontalLineToRelative(-8.0f)
                lineToRelative(-2.0f, -2.0f)
                close()
            }
        }
        return _folder!!
    }

private var _folder: ImageVector? = null

internal val Icons.Outlined.Restore: ImageVector
    get() {
        if (_restore != null) {
            return _restore!!
        }
        _restore = materialIcon(name = "Outlined.Restore") {
            materialPath {
                moveTo(13.0f, 3.0f)
                curveToRelative(-4.97f, 0.0f, -9.0f, 4.03f, -9.0f, 9.0f)
                lineTo(1.0f, 12.0f)
                lineToRelative(4.0f, 3.99f)
                lineTo(9.0f, 12.0f)
                lineTo(6.0f, 12.0f)
                curveToRelative(0.0f, -3.87f, 3.13f, -7.0f, 7.0f, -7.0f)
                reflectiveCurveToRelative(7.0f, 3.13f, 7.0f, 7.0f)
                reflectiveCurveToRelative(-3.13f, 7.0f, -7.0f, 7.0f)
                curveToRelative(-1.93f, 0.0f, -3.68f, -0.79f, -4.94f, -2.06f)
                lineToRelative(-1.42f, 1.42f)
                curveTo(8.27f, 19.99f, 10.51f, 21.0f, 13.0f, 21.0f)
                curveToRelative(4.97f, 0.0f, 9.0f, -4.03f, 9.0f, -9.0f)
                reflectiveCurveToRelative(-4.03f, -9.0f, -9.0f, -9.0f)
                close()
                moveTo(12.0f, 8.0f)
                verticalLineToRelative(5.0f)
                lineToRelative(4.25f, 2.52f)
                lineToRelative(0.77f, -1.28f)
                lineToRelative(-3.52f, -2.09f)
                lineTo(13.5f, 8.0f)
                close()
            }
        }
        return _restore!!
    }

private var _restore: ImageVector? = null

internal val Icons.Outlined.ThumbsUpDown: ImageVector
    get() {
        if (_thumbsUpDown != null) {
            return _thumbsUpDown!!
        }
        _thumbsUpDown = materialIcon(name = "Outlined.ThumbsUpDown") {
            materialPath {
                moveTo(12.0f, 6.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                lineTo(5.82f, 5.0f)
                lineToRelative(0.66f, -3.18f)
                lineToRelative(0.02f, -0.23f)
                curveToRelative(0.0f, -0.31f, -0.13f, -0.59f, -0.33f, -0.8f)
                lineTo(5.38f, 0.0f)
                lineTo(0.44f, 4.94f)
                curveTo(0.17f, 5.21f, 0.0f, 5.59f, 0.0f, 6.0f)
                verticalLineToRelative(6.5f)
                curveToRelative(0.0f, 0.83f, 0.67f, 1.5f, 1.5f, 1.5f)
                horizontalLineToRelative(6.75f)
                curveToRelative(0.62f, 0.0f, 1.15f, -0.38f, 1.38f, -0.91f)
                lineToRelative(2.26f, -5.29f)
                curveToRelative(0.07f, -0.17f, 0.11f, -0.36f, 0.11f, -0.55f)
                lineTo(12.0f, 6.0f)
                close()
                moveTo(10.0f, 7.13f)
                lineTo(7.92f, 12.0f)
                lineTo(2.0f, 12.0f)
                lineTo(2.0f, 6.21f)
                lineToRelative(1.93f, -1.93f)
                lineTo(3.36f, 7.0f)
                lineTo(10.0f, 7.0f)
                verticalLineToRelative(0.13f)
                close()
                moveTo(22.5f, 10.0f)
                horizontalLineToRelative(-6.75f)
                curveToRelative(-0.62f, 0.0f, -1.15f, 0.38f, -1.38f, 0.91f)
                lineToRelative(-2.26f, 5.29f)
                curveToRelative(-0.07f, 0.17f, -0.11f, 0.36f, -0.11f, 0.55f)
                lineTo(12.0f, 18.0f)
                curveToRelative(0.0f, 0.55f, 0.45f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(5.18f)
                lineToRelative(-0.66f, 3.18f)
                lineToRelative(-0.02f, 0.24f)
                curveToRelative(0.0f, 0.31f, 0.13f, 0.59f, 0.33f, 0.8f)
                lineToRelative(0.79f, 0.78f)
                lineToRelative(4.94f, -4.94f)
                curveToRelative(0.27f, -0.27f, 0.44f, -0.65f, 0.44f, -1.06f)
                verticalLineToRelative(-6.5f)
                curveToRelative(0.0f, -0.83f, -0.67f, -1.5f, -1.5f, -1.5f)
                close()
                moveTo(22.0f, 17.79f)
                lineToRelative(-1.93f, 1.93f)
                lineToRelative(0.57f, -2.72f)
                lineTo(14.0f, 17.0f)
                verticalLineToRelative(-0.13f)
                lineTo(16.08f, 12.0f)
                lineTo(22.0f, 12.0f)
                verticalLineToRelative(5.79f)
                close()
            }
        }
        return _thumbsUpDown!!
    }

private var _thumbsUpDown: ImageVector? = null

internal val Icons.Filled.Plates: ImageVector
    get() {
        if (_plates != null) {
            return _plates!!
        }
        _plates = Builder(
            name = "Plates", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(13.9781f, 2.9534f)
                    lineToRelative(7.0332f, 16.5691f)
                    lineToRelative(-1.841f, 0.7815f)
                    lineToRelative(-7.0332f, -16.5691f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(5.0f, 11.0f)
                    horizontalLineToRelative(9.0f)
                    verticalLineToRelative(2.0f)
                    horizontalLineToRelative(-9.0f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(4.0f, 15.0f)
                    horizontalLineToRelative(11.0f)
                    verticalLineToRelative(2.0f)
                    horizontalLineToRelative(-11.0f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(3.0f, 19.0f)
                    horizontalLineToRelative(14.0f)
                    verticalLineToRelative(2.0f)
                    horizontalLineToRelative(-14.0f)
                    close()
                }
            }
        }
            .build()
        return _plates!!
    }

private var _plates: ImageVector? = null

internal val Icons.Outlined.Dialpad: ImageVector
    get() {
        if (_dialpad != null) {
            return _dialpad!!
        }
        _dialpad = materialIcon(name = "Outlined.Dialpad") {
            materialPath {
                moveTo(12.0f, 19.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(6.0f, 1.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(6.0f, 7.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(6.0f, 13.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(18.0f, 5.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                reflectiveCurveToRelative(-2.0f, 0.9f, -2.0f, 2.0f)
                reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                close()
                moveTo(12.0f, 13.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(18.0f, 13.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(18.0f, 7.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(12.0f, 7.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(12.0f, 1.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                close()
            }
        }
        return _dialpad!!
    }

private var _dialpad: ImageVector? = null

internal val Icons.Outlined.Backspace: ImageVector
    get() {
        if (_backspace != null) {
            return _backspace!!
        }
        _backspace = materialIcon(name = "Outlined.Backspace") {
            materialPath {
                moveTo(22.0f, 3.0f)
                lineTo(7.0f, 3.0f)
                curveToRelative(-0.69f, 0.0f, -1.23f, 0.35f, -1.59f, 0.88f)
                lineTo(0.0f, 12.0f)
                lineToRelative(5.41f, 8.11f)
                curveToRelative(0.36f, 0.53f, 0.9f, 0.89f, 1.59f, 0.89f)
                horizontalLineToRelative(15.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                lineTo(24.0f, 5.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(22.0f, 19.0f)
                lineTo(7.07f, 19.0f)
                lineTo(2.4f, 12.0f)
                lineToRelative(4.66f, -7.0f)
                lineTo(22.0f, 5.0f)
                verticalLineToRelative(14.0f)
                close()
                moveTo(10.41f, 17.0f)
                lineTo(14.0f, 13.41f)
                lineTo(17.59f, 17.0f)
                lineTo(19.0f, 15.59f)
                lineTo(15.41f, 12.0f)
                lineTo(19.0f, 8.41f)
                lineTo(17.59f, 7.0f)
                lineTo(14.0f, 10.59f)
                lineTo(10.41f, 7.0f)
                lineTo(9.0f, 8.41f)
                lineTo(12.59f, 12.0f)
                lineTo(9.0f, 15.59f)
                close()
            }
        }
        return _backspace!!
    }

private var _backspace: ImageVector? = null

