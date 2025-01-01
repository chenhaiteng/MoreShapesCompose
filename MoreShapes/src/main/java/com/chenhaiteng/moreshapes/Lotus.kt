package com.chenhaiteng.moreshapes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.fitSquare
import androidx.compose.ui.geometry.cubicTo
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.rotationMatrix
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Lotus(val petals: Int = 6, val petalHeightRatio: Float = 0.5f, val controlFactor: Offset = Offset(0.9f, 0.8f), val topFactor: Float = 0.3f) : CustomShape {
    override fun toPath(rect: Rect): Path =
        Path.lotus(rect,
            petals = this.petals,
            depthRatio = 1.0f - petalHeightRatio,
            controlFactor = controlFactor,
            topFactor = topFactor)
}

fun Path.Companion.lotus(rect: Rect, petals: Int, depthRatio: Float, controlFactor: Offset, topFactor: Float) = Path().apply {
    val r = rect.fitSquare()
    val radius = r.size.height / 2f
    val depth = radius * depthRatio
    val degree = 360f / petals
    val startOffset = -degree / 2f
    val ratioX = controlFactor.x
    val ratioY = controlFactor.y
    val start = Matrix.rotationMatrix(startOffset, r.center)
        .map(Offset(r.center.x, r.center.y - depth))
    val top = Offset(r.center.x, r.top)
    val c1 = Offset(start.x - (top.x - start.x) * ratioX, start.y - (start.y - top.y) * ratioY)
    val c2 = Offset(top.x, top.y - (top.y - start.y) * topFactor)
    val next = Matrix.rotationMatrix(-startOffset, r.center)
        .map(Offset(r.center.x, r.center.y - depth))
    val c3 = Offset(next.x + (next.x - top.x) * ratioX, next.y - (next.y - top.y) * ratioY)
    moveTo(start.x, start.y)
    for (i in 0..<petals) {
        val m = Matrix.rotationMatrix(i * degree, r.center)
        val tempTop = m.map(top)
        val tempNext = m.map(next)
        val tempC1 = m.map(c1)
        val tempC2 = m.map(c2)
        val tempC3 = m.map(c3)
        cubicTo(tempC1, tempC2, tempTop)
        cubicTo(tempC2, tempC3, tempNext)
    }
    close()
}

@Preview(showBackground = true)
@Composable
fun LotusPreview() {
    val lotus = Lotus()
    CustomShapeView(modifier = Modifier.fillMaxSize(), customShape = lotus, color = Color.Red)
    CustomShapeView(modifier = Modifier.padding(20.dp).fillMaxSize(), customShape = lotus, color = Color.Blue, style = Stroke(width = 5.0f))
}