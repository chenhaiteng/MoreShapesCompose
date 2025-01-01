package com.chenhaiteng.moreshapes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.fitSquare
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill

interface CustomShape {
    fun toPath(rect: Rect): Path
}

@Composable
fun CustomShapeView(modifier: Modifier,
                    customShape: CustomShape,
                    color: Color,
                    /*@FloatRange(from = 0.0, to = 1.0)*/
                    alpha: Float = 1.0f,
                    style: DrawStyle = Fill,
                    colorFilter: ColorFilter? = null,
                    blendMode: BlendMode = DrawScope.DefaultBlendMode, insetRatio: Float = 0f)
{
    Canvas(modifier) {
        drawPath(customShape.toPath(rect = size.fitSquare(center, insetRatio = insetRatio)), color, alpha, style, colorFilter, blendMode)
    }
}
@Composable
fun CustomShapeView(modifier: Modifier,
                    customShape: CustomShape,
                    brush: Brush,
                    /*@FloatRange(from = 0.0, to = 1.0)*/
                    alpha: Float = 1.0f,
                    style: DrawStyle = Fill,
                    colorFilter: ColorFilter? = null,
                    blendMode: BlendMode = DrawScope.DefaultBlendMode, insetRatio: Float = 0f)
{
    Canvas(modifier) {
        drawPath(customShape.toPath(rect = size.fitSquare(center, insetRatio = insetRatio)), brush, alpha, style, colorFilter, blendMode)
    }
}