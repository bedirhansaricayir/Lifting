package com.lifting.app.core.ui.components.chart_redesig.line

/**
 * Created by bedirhansaricayir on 03.06.2025
 */

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas

internal fun DrawScope.drawLineGradient(
    path: Path,
    color1: Color,
    color2: Color,
    progress: Float,
    size: Size? = null,
    startOffset: Float,
    endOffset: Float
) {
    val _size = size ?: this.size
    drawIntoCanvas {
        val p = Path()
        p.addPath(path)
        p.lineTo(endOffset, _size.height)
        p.lineTo(startOffset, _size.height)
        p.close()
        val paint = Paint()
        paint.shader = LinearGradientShader(
            Offset(0f, 0f),
            Offset(0f, _size.height),
            listOf(
                color1.copy(alpha = color1.alpha * progress),
                color2,
            ),
            tileMode = TileMode.Mirror
        )
        it.drawPath(p, paint)
    }
}