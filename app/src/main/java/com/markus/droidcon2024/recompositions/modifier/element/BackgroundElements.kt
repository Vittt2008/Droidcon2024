package com.markus.droidcon2024.recompositions.modifier.element

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.LayoutDirection

internal class StateBackgroundElement(
    private val color: State<Color> = mutableStateOf(Color.Unspecified),
    private val brush: State<Brush>? = null,
    private val alpha: Float,
    private val shape: Shape,
    private val inspectorInfo: InspectorInfo.() -> Unit,
) : ModifierNodeElement<StateBackgroundNode>() {

    override fun create(): StateBackgroundNode {
        return StateBackgroundNode(
            color,
            brush,
            alpha,
            shape
        )
    }

    override fun update(node: StateBackgroundNode) {
        node.color = color
        node.brush = brush
        node.alpha = alpha
        node.shape = shape
    }

    override fun InspectorInfo.inspectableProperties() {
        inspectorInfo()
    }

    override fun hashCode(): Int {
        var result = color.hashCode()
        result = 31 * result + (brush?.hashCode() ?: 0)
        result = 31 * result + alpha.hashCode()
        result = 31 * result + shape.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        val otherModifier = other as? StateBackgroundNode ?: return false
        return color == otherModifier.color &&
                brush == otherModifier.brush &&
                alpha == otherModifier.alpha &&
                shape == otherModifier.shape
    }
}

internal class StateBackgroundNode(
    var color: State<Color> = mutableStateOf(Color.Unspecified),
    var brush: State<Brush>? = null,
    var alpha: Float,
    var shape: Shape,
) : DrawModifierNode, Modifier.Node() {

    // naive cache outline calculation if size is the same
    private var lastSize: Size? = null
    private var lastLayoutDirection: LayoutDirection? = null
    private var lastOutline: Outline? = null
    private var lastShape: Shape? = null

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
        if (color.value != Color.Unspecified) {
            drawRect(color = color.value)
        }
        brush?.let { drawRect(brush = it.value, alpha = alpha) }
    }

    private fun ContentDrawScope.drawOutline() {
        val outline =
            if (size == lastSize && layoutDirection == lastLayoutDirection && lastShape == shape) {
                lastOutline!!
            } else {
                shape.createOutline(size, layoutDirection, this)
            }
        if (color.value != Color.Unspecified) drawOutline(outline, color = color.value)
        brush?.let { drawOutline(outline, brush = it.value, alpha = alpha) }
        lastOutline = outline
        lastSize = size
        lastLayoutDirection = layoutDirection
        lastShape = shape
    }
}

internal class LambdaBackgroundElement(
    private val color: () -> Color = { Color.Unspecified },
    private val brush: (() -> Brush)? = null,
    private val alpha: Float,
    private val shape: Shape,
    private val inspectorInfo: InspectorInfo.() -> Unit,
) : ModifierNodeElement<LambdaBackgroundNode>() {

    override fun create(): LambdaBackgroundNode {
        return LambdaBackgroundNode(
            color,
            brush,
            alpha,
            shape
        )
    }

    override fun update(node: LambdaBackgroundNode) {
        node.color = color
        node.brush = brush
        node.alpha = alpha
        node.shape = shape
    }

    override fun InspectorInfo.inspectableProperties() {
        inspectorInfo()
    }

    override fun hashCode(): Int {
        var result = color.hashCode()
        result = 31 * result + (brush?.hashCode() ?: 0)
        result = 31 * result + alpha.hashCode()
        result = 31 * result + shape.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        val otherModifier = other as? StateBackgroundNode ?: return false
        return color == otherModifier.color &&
                brush == otherModifier.brush &&
                alpha == otherModifier.alpha &&
                shape == otherModifier.shape
    }
}

internal class LambdaBackgroundNode(
    var color: (() -> Color) = { Color.Unspecified },
    var brush: (() -> Brush)? = null,
    var alpha: Float,
    var shape: Shape,
) : DrawModifierNode, Modifier.Node() {

    // naive cache outline calculation if size is the same
    private var lastSize: Size? = null
    private var lastLayoutDirection: LayoutDirection? = null
    private var lastOutline: Outline? = null
    private var lastShape: Shape? = null

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
        if (color.invoke() != Color.Unspecified) {
            drawRect(color = color.invoke())
        }
        brush?.let { drawRect(brush = it.invoke(), alpha = alpha) }
    }

    private fun ContentDrawScope.drawOutline() {
        val outline =
            if (size == lastSize && layoutDirection == lastLayoutDirection && lastShape == shape) {
                lastOutline!!
            } else {
                shape.createOutline(size, layoutDirection, this)
            }
        if (color.invoke() != Color.Unspecified) drawOutline(outline, color = color.invoke())
        brush?.let { drawOutline(outline, brush = it.invoke(), alpha = alpha) }
        lastOutline = outline
        lastSize = size
        lastLayoutDirection = layoutDirection
        lastShape = shape
    }
}