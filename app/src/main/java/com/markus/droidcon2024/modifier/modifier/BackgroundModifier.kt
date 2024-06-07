package com.example.flo.compose.recomposition.modifier.modifier

import androidx.compose.runtime.State
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.InspectorValueInfo
import androidx.compose.ui.unit.LayoutDirection

internal class StateBackgroundModifier(
    private val color: State<Color>? = null,
    private val brush: State<Brush>? = null,
    private val alpha: Float = 1.0f,
    private val shape: Shape,
    inspectorInfo: InspectorInfo.() -> Unit,
) : DrawModifier, InspectorValueInfo(inspectorInfo) {

    // naive cache outline calculation if size is the same
    private var lastSize: Size? = null
    private var lastLayoutDirection: LayoutDirection? = null
    private var lastOutline: Outline? = null

    override fun ContentDrawScope.draw() {
        if (shape === RectangleShape) {
            // shortcut to avoid Outline calculation and allocation
            drawRect()
        } else {
            drawOutline()
        }
        drawContent()
    }

    private fun ContentDrawScope.drawRect() {
        color?.let { drawRect(color = it.value) }
        brush?.let { drawRect(brush = it.value, alpha = alpha) }
    }

    private fun ContentDrawScope.drawOutline() {
        val outline =
            if (size == lastSize && layoutDirection == lastLayoutDirection) {
                lastOutline!!
            } else {
                shape.createOutline(size, layoutDirection, this)
            }
        color?.let { drawOutline(outline, color = color.value) }
        brush?.let { drawOutline(outline, brush = brush.value, alpha = alpha) }
        lastOutline = outline
        lastSize = size
        lastLayoutDirection = layoutDirection
    }

    override fun hashCode(): Int {
        var result = color?.hashCode() ?: 0
        result = 31 * result + (brush?.hashCode() ?: 0)
        result = 31 * result + alpha.hashCode()
        result = 31 * result + shape.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        val otherModifier = other as? StateBackgroundModifier ?: return false
        return color == otherModifier.color &&
                brush == otherModifier.brush &&
                alpha == otherModifier.alpha &&
                shape == otherModifier.shape
    }

    override fun toString(): String =
        "Background(color=$color, brush=$brush, alpha = $alpha, shape=$shape)"
}

internal class LambdaBackgroundModifier(
    private val color: (() -> Color)? = null,
    private val brush: (() -> Brush)? = null,
    private val alpha: Float = 1.0f,
    private val shape: Shape,
    inspectorInfo: InspectorInfo.() -> Unit,
) : DrawModifier, InspectorValueInfo(inspectorInfo) {

    // naive cache outline calculation if size is the same
    private var lastSize: Size? = null
    private var lastLayoutDirection: LayoutDirection? = null
    private var lastOutline: Outline? = null

    override fun ContentDrawScope.draw() {
        if (shape === RectangleShape) {
            // shortcut to avoid Outline calculation and allocation
            drawRect()
        } else {
            drawOutline()
        }
        drawContent()
    }

    private fun ContentDrawScope.drawRect() {
        color?.let { drawRect(color = it.invoke()) }
        brush?.let { drawRect(brush = it.invoke(), alpha = alpha) }
    }

    private fun ContentDrawScope.drawOutline() {
        val outline =
            if (size == lastSize && layoutDirection == lastLayoutDirection) {
                lastOutline!!
            } else {
                shape.createOutline(size, layoutDirection, this)
            }
        color?.let { drawOutline(outline, color = color.invoke()) }
        brush?.let { drawOutline(outline, brush = brush.invoke(), alpha = alpha) }
        lastOutline = outline
        lastSize = size
        lastLayoutDirection = layoutDirection
    }

    override fun hashCode(): Int {
        var result = color?.hashCode() ?: 0
        result = 31 * result + (brush?.hashCode() ?: 0)
        result = 31 * result + alpha.hashCode()
        result = 31 * result + shape.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        val otherModifier = other as? LambdaBackgroundModifier ?: return false
        return color == otherModifier.color &&
                brush == otherModifier.brush &&
                alpha == otherModifier.alpha &&
                shape == otherModifier.shape
    }

    override fun toString(): String =
        "Background(color=$color, brush=$brush, alpha = $alpha, shape=$shape)"
}
