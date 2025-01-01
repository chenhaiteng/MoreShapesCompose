package com.chenhaiteng.moreshapes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.cubicTo
import androidx.compose.ui.geometry.fitSquare
import androidx.compose.ui.geometry.lineTo
import androidx.compose.ui.geometry.moveTo
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.rotatedSweepGradient
import androidx.compose.ui.graphics.rotationMatrix
import androidx.compose.ui.tooling.preview.Preview


data class RectangleLotus(val petals: Int = 6,
                          val petalHeightRatio: Float = 0.2f,
                          val turnPositionRatio: Float = 0.5f,
                          val topControlFactor: Float = 0.5f,
                          val turnControlFactor: Float = 0.25f,
                          val innerGap: Float = 0.1f,
                          val insetDegrees: Float = 1.0f,
                          val style: DrawStyle = Fill) : CustomShape {
    override fun toPath(rect: Rect): Path = if(style == Fill) {
        Path.filledRectangleLotus(
            rect, petals,
            hRatio = petalHeightRatio,
            tRatio = turnPositionRatio,
            topControlFactor = topControlFactor
        )
    } else {
        Path.rectangleLotus(
            rect, petals,
            hRatio = petalHeightRatio,
            tRatio = turnPositionRatio,
            topControlFactor = topControlFactor,
            innerGap = innerGap,
            insetDegrees = insetDegrees
        )
    }
}

fun Path.Companion.rectangleLotus(
    rect: Rect,
    petals: Int = 6,
    hRatio: Float = 0.2f,
    tRatio: Float = 0.5f,
    topControlFactor: Float = 0.25f,
    turnControlFactor: Float = 0.5f,
    innerGap: Float = 0.1f,
    insetDegrees: Float = 1.0f) = Path().apply {
    val r = rect.fitSquare()
    val degree = 360f/petals
    val petalHeight = hRatio*r.height*0.5f
    val matrix = Matrix.rotationMatrix(-degree/2f, r.center)
    val start = matrix.map(Offset(r.center.x, r.top + petalHeight))
    val turn1 = matrix.map(Offset(r.center.x, r.top + tRatio*petalHeight))
    val turn1control1 = matrix.map(Offset(r.center.x, r.top + tRatio*petalHeight - petalHeight*turnControlFactor))
    val top = Offset(r.center.x, r.top)
    val topControl = Offset(top.x, top.y + petalHeight*topControlFactor)
    val turn2 = Matrix.rotationMatrix(degree/2f, r.center).map(Offset(r.center.x, r.top + tRatio*petalHeight))
    val turn2Control2 = Matrix.rotationMatrix(degree/2f, r.center).map(Offset(r.center.x, r.top + tRatio*petalHeight - petalHeight*turnControlFactor))
    val next = Matrix.rotationMatrix(degree/2f, r.center).map(Offset(r.center.x, r.top + petalHeight))
    moveTo(start)
    for (i in 0..<petals) {
        val m = Matrix.rotationMatrix(i*degree, r.center)
        lineTo(m.map(turn1))
        cubicTo(m.map(turn1control1), m.map(topControl), m.map(top))
        cubicTo(m.map(topControl), m.map(turn2Control2), m.map(turn2))
        lineTo(m.map(next))
    }
    val innerOffset = petalHeight*innerGap
    val innerTurn1 = Matrix.rotationMatrix(-degree*0.5f - insetDegrees, r.center).map(Offset(r.center.x, r.top + tRatio*petalHeight))
    val innerTurn1Control = Matrix.rotationMatrix(-degree*0.5f - insetDegrees, r.center).map(Offset(r.center.x, r.top + tRatio*petalHeight - petalHeight*turnControlFactor + innerOffset))
    val innerTop = Matrix.rotationMatrix(-degree, r.center).map(top)
    val innerTopControl = Matrix.rotationMatrix(-degree, r.center).map(Offset(top.x, top.y + petalHeight*topControlFactor + innerOffset))
    val innerTurn2 = Matrix.rotationMatrix(-degree*1.5f + insetDegrees, r.center).map(Offset(r.center.x, r.top + tRatio*petalHeight))
    val innerTurn2Control = Matrix.rotationMatrix(-degree*1.5f + insetDegrees, r.center).map(Offset(r.center.x, r.top + tRatio*petalHeight - petalHeight*turnControlFactor + innerOffset))
    val innerNext = Matrix.rotationMatrix(-degree*1.5f, r.center).map(Offset(r.center.x, r.top + petalHeight))
    for (i in 0..<petals) {
        val m = Matrix.rotationMatrix(-degree*i, r.center)
        lineTo(m.map(innerTurn1))
        cubicTo(m.map(innerTurn1Control), m.map(innerTopControl), m.map(innerTop))
        cubicTo(m.map(innerTopControl), m.map(innerTurn2Control), m.map(innerTurn2))
        lineTo(m.map(innerNext))
    }
}
fun Path.Companion.filledRectangleLotus(
    rect: Rect,
    petals: Int = 6,
    hRatio: Float = 0.2f,
    tRatio: Float = 0.5f,
    topControlFactor: Float = 0.25f,
    turnControlFactor: Float = 0.5f) = Path().apply {
    val r = rect.fitSquare()
    val degree = 360f/petals
    val petalHeight = hRatio*r.height*0.5f
    val matrix = Matrix.rotationMatrix(-degree/2f, r.center)
    val start = matrix.map(Offset(r.center.x, r.top + petalHeight))
    val turn1 = matrix.map(Offset(r.center.x, r.top + tRatio*petalHeight))
    val turn1control1 = matrix.map(Offset(r.center.x, r.top + tRatio*petalHeight - petalHeight*turnControlFactor))
    val top = Offset(r.center.x, r.top)
    val topControl = Offset(top.x, top.y + petalHeight*topControlFactor)
    val turn2 = Matrix.rotationMatrix(degree/2f, r.center).map(Offset(r.center.x, r.top + tRatio*petalHeight))
    val turn2Control2 = Matrix.rotationMatrix(degree/2f, r.center).map(Offset(r.center.x, r.top + tRatio*petalHeight - petalHeight*turnControlFactor))
    val next = Matrix.rotationMatrix(degree/2f, r.center).map(Offset(r.center.x, r.top + petalHeight))
    moveTo(start)
    for (i in 0..<petals) {
        val m = Matrix.rotationMatrix(i*degree, r.center)
        lineTo(m.map(turn1))
        cubicTo(m.map(turn1control1), m.map(topControl), m.map(top))
        cubicTo(m.map(topControl), m.map(turn2Control2), m.map(turn2))
        lineTo(m.map(next))
    }
}
@Preview(showBackground = true)
@Composable
fun RectangleLotusPreview() {
    CustomShapeView(
        modifier = Modifier.fillMaxSize(),
        RectangleLotus(petalHeightRatio = 0.5f,
            petals = 8,
            turnControlFactor = 0.2f,
            topControlFactor = 0.5f,
            turnPositionRatio = 0.5f, style = Stroke()),
        Brush.rotatedSweepGradient(colorStops = arrayOf(
            0.0f to Color.Blue,
            0.5f to Color.Red,
            1.0f to Color.Blue
        ), degree = 0.0f)
    )
}