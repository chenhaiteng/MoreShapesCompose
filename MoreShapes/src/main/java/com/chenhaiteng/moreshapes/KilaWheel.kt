package com.chenhaiteng.moreshapes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.fitSquare
import androidx.compose.ui.geometry.lineTo
import androidx.compose.ui.geometry.moveTo
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.rotationMatrix
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


data class KilaWheel(val kilaCount: Int = 10, val kilaHeightRatio: Float = 0.15f, val kilaWidthRatio: Float = 0.9f, val controlFactor: Float = 0.05f) : CustomShape {
    override fun toPath(rect: Rect): Path
            = kilaWheel(rect,
        hRatio = kilaHeightRatio,
        wRatio = kilaWidthRatio,
        count = kilaCount,
        controlFactor = controlFactor)
}

fun kilaWheel(
    rect: Rect,
    hRatio: Float = 0.15f,
    wRatio: Float = 0.9f,
    count: Int = 10,
    controlFactor: Float = 0.05f) = Path().apply {
    val r = rect.fitSquare()
    val degree = 360.0f/count
    val radius = (1.0f - hRatio) * r.size.height/2.0f
    val top = Offset(r.center.x, r.top)
    val bottom = Offset(r.center.x, r.center.y - radius)
    val start = Matrix.rotationMatrix(-degree*0.5f, rect.center).map(bottom)
    val next = Matrix.rotationMatrix(degree*0.5f, rect.center).map(bottom)
    val kilaWidth = next.x - start.x
    val anchorOffset = kilaWidth * (1f - wRatio)/2f
    val lAnchor = Offset(start.x + anchorOffset, bottom.y)
    val rAnchor = Offset(next.x - anchorOffset, bottom.y)
    val controlX = (anchorOffset)/2f + kilaWidth * controlFactor
    val controlY = (start.y - bottom.y)/2f
    val lControl = Offset(lAnchor.x + controlX, lAnchor.y + controlY)
    val rControl = Offset(rAnchor.x - controlX, rAnchor.y + controlY)
    moveTo(start)

    for (i in 0..<count) {
        val m = Matrix.rotationMatrix(i * degree, r.center)
        val lc = m.map(lControl)
        val la = m.map(lAnchor)
        val t = m.map(top)
        val rc = m.map(rControl)
        val ra = m.map(rAnchor)
        val n = m.map(next)
        quadraticTo(lc.x, lc.y, la.x, la.y)
        lineTo(t)
        lineTo(ra)
        quadraticTo(rc.x, rc.y, n.x, n.y)
    }
}

@Preview(showBackground = true)
@Composable
fun KilaWheelPreview() {
    val kila = KilaWheel(kilaCount = 12)
    CustomShapeView(modifier = Modifier.fillMaxSize(), customShape = kila, color = Color.Red, style = Stroke(width = 5.0f))
    CustomShapeView(modifier = Modifier.padding(20.dp).fillMaxSize(), customShape = kila, color = Color.Blue)
}