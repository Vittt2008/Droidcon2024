package com.example.flo.compose.recomposition.modifier.element

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.constrain
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth

internal class StateSizeElement(
    private val minWidth: State<Dp> = mutableStateOf(Dp.Unspecified),
    private val minHeight: State<Dp> = mutableStateOf(Dp.Unspecified),
    private val maxWidth: State<Dp> = mutableStateOf(Dp.Unspecified),
    private val maxHeight: State<Dp> = mutableStateOf(Dp.Unspecified),
    private val enforceIncoming: Boolean,
    private val inspectorInfo: InspectorInfo.() -> Unit
) : ModifierNodeElement<StateSizeNode>() {
    override fun create(): StateSizeNode =
        StateSizeNode(
            minWidth = minWidth,
            minHeight = minHeight,
            maxWidth = maxWidth,
            maxHeight = maxHeight,
            enforceIncoming = enforceIncoming
        )

    override fun update(node: StateSizeNode) {
        node.minWidth = minWidth
        node.minHeight = minHeight
        node.maxWidth = maxWidth
        node.maxHeight = maxHeight
        node.enforceIncoming = enforceIncoming
    }

    override fun InspectorInfo.inspectableProperties() {
        inspectorInfo()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StateSizeElement) return false

        if (minWidth != other.minWidth) return false
        if (minHeight != other.minHeight) return false
        if (maxWidth != other.maxWidth) return false
        if (maxHeight != other.maxHeight) return false
        if (enforceIncoming != other.enforceIncoming) return false

        return true
    }

    override fun hashCode(): Int {
        var result = minWidth.hashCode()
        result = 31 * result + minHeight.hashCode()
        result = 31 * result + maxWidth.hashCode()
        result = 31 * result + maxHeight.hashCode()
        result = 31 * result + enforceIncoming.hashCode()
        return result
    }
}

internal class StateSizeNode(
    var minWidth: State<Dp> = mutableStateOf(Dp.Unspecified),
    var minHeight: State<Dp> = mutableStateOf(Dp.Unspecified),
    var maxWidth: State<Dp> = mutableStateOf(Dp.Unspecified),
    var maxHeight: State<Dp> = mutableStateOf(Dp.Unspecified),
    var enforceIncoming: Boolean
) : LayoutModifierNode, Modifier.Node() {
    private val Density.targetConstraints: Constraints
        get() {
            val maxWidth = if (maxWidth.value != Dp.Unspecified) {
                maxWidth.value.roundToPx().coerceAtLeast(0)
            } else {
                Constraints.Infinity
            }
            val maxHeight = if (maxHeight.value != Dp.Unspecified) {
                maxHeight.value.roundToPx().coerceAtLeast(0)
            } else {
                Constraints.Infinity
            }
            val minWidth = if (minWidth.value != Dp.Unspecified) {
                minWidth.value.roundToPx().coerceAtMost(maxWidth).coerceAtLeast(0).let {
                    if (it != Constraints.Infinity) it else 0
                }
            } else {
                0
            }
            val minHeight = if (minHeight.value != Dp.Unspecified) {
                minHeight.value.roundToPx().coerceAtMost(maxHeight).coerceAtLeast(0).let {
                    if (it != Constraints.Infinity) it else 0
                }
            } else {
                0
            }
            return Constraints(
                minWidth = minWidth,
                minHeight = minHeight,
                maxWidth = maxWidth,
                maxHeight = maxHeight
            )
        }

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val wrappedConstraints = targetConstraints.let { targetConstraints ->
            if (enforceIncoming) {
                constraints.constrain(targetConstraints)
            } else {
                val resolvedMinWidth = if (minWidth.value != Dp.Unspecified) {
                    targetConstraints.minWidth
                } else {
                    constraints.minWidth.coerceAtMost(targetConstraints.maxWidth)
                }
                val resolvedMaxWidth = if (maxWidth.value != Dp.Unspecified) {
                    targetConstraints.maxWidth
                } else {
                    constraints.maxWidth.coerceAtLeast(targetConstraints.minWidth)
                }
                val resolvedMinHeight = if (minHeight.value != Dp.Unspecified) {
                    targetConstraints.minHeight
                } else {
                    constraints.minHeight.coerceAtMost(targetConstraints.maxHeight)
                }
                val resolvedMaxHeight = if (maxHeight.value != Dp.Unspecified) {
                    targetConstraints.maxHeight
                } else {
                    constraints.maxHeight.coerceAtLeast(targetConstraints.minHeight)
                }
                Constraints(
                    resolvedMinWidth,
                    resolvedMaxWidth,
                    resolvedMinHeight,
                    resolvedMaxHeight
                )
            }
        }
        val placeable = measurable.measure(wrappedConstraints)
        return layout(placeable.width, placeable.height) {
            placeable.placeRelative(0, 0)
        }
    }

    override fun IntrinsicMeasureScope.minIntrinsicWidth(
        measurable: IntrinsicMeasurable,
        height: Int
    ): Int {
        val constraints = targetConstraints
        return if (constraints.hasFixedWidth) {
            constraints.maxWidth
        } else {
            constraints.constrainWidth(measurable.minIntrinsicWidth(height))
        }
    }

    override fun IntrinsicMeasureScope.minIntrinsicHeight(
        measurable: IntrinsicMeasurable,
        width: Int
    ): Int {
        val constraints = targetConstraints
        return if (constraints.hasFixedHeight) {
            constraints.maxHeight
        } else {
            constraints.constrainHeight(measurable.minIntrinsicHeight(width))
        }
    }

    override fun IntrinsicMeasureScope.maxIntrinsicWidth(
        measurable: IntrinsicMeasurable,
        height: Int
    ): Int {
        val constraints = targetConstraints
        return if (constraints.hasFixedWidth) {
            constraints.maxWidth
        } else {
            constraints.constrainWidth(measurable.maxIntrinsicWidth(height))
        }
    }

    override fun IntrinsicMeasureScope.maxIntrinsicHeight(
        measurable: IntrinsicMeasurable,
        width: Int
    ): Int {
        val constraints = targetConstraints
        return if (constraints.hasFixedHeight) {
            constraints.maxHeight
        } else {
            constraints.constrainHeight(measurable.maxIntrinsicHeight(width))
        }
    }
}

// ===

internal class LambdaSizeElement(
    private val minWidth: () -> Dp = { Dp.Unspecified },
    private val minHeight: () -> Dp = { Dp.Unspecified },
    private val maxWidth: () -> Dp = { Dp.Unspecified },
    private val maxHeight: () -> Dp = { Dp.Unspecified },
    private val enforceIncoming: Boolean,
    private val inspectorInfo: InspectorInfo.() -> Unit
) : ModifierNodeElement<LambdaSizeNode>() {
    override fun create(): LambdaSizeNode =
        LambdaSizeNode(
            minWidth = minWidth,
            minHeight = minHeight,
            maxWidth = maxWidth,
            maxHeight = maxHeight,
            enforceIncoming = enforceIncoming
        )

    override fun update(node: LambdaSizeNode) {
        node.minWidth = minWidth
        node.minHeight = minHeight
        node.maxWidth = maxWidth
        node.maxHeight = maxHeight
        node.enforceIncoming = enforceIncoming
    }

    override fun InspectorInfo.inspectableProperties() {
        inspectorInfo()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LambdaSizeElement) return false

        if (minWidth != other.minWidth) return false
        if (minHeight != other.minHeight) return false
        if (maxWidth != other.maxWidth) return false
        if (maxHeight != other.maxHeight) return false
        if (enforceIncoming != other.enforceIncoming) return false

        return true
    }

    override fun hashCode(): Int {
        var result = minWidth.hashCode()
        result = 31 * result + minHeight.hashCode()
        result = 31 * result + maxWidth.hashCode()
        result = 31 * result + maxHeight.hashCode()
        result = 31 * result + enforceIncoming.hashCode()
        return result
    }
}

internal class LambdaSizeNode(
    var minWidth: () -> Dp = { Dp.Unspecified },
    var minHeight: () -> Dp = { Dp.Unspecified },
    var maxWidth: () -> Dp = { Dp.Unspecified },
    var maxHeight: () -> Dp = { Dp.Unspecified },
    var enforceIncoming: Boolean
) : LayoutModifierNode, Modifier.Node() {
    private val Density.targetConstraints: Constraints
        get() {
            val maxWidth = if (maxWidth() != Dp.Unspecified) {
                maxWidth().roundToPx().coerceAtLeast(0)
            } else {
                Constraints.Infinity
            }
            val maxHeight = if (maxHeight.invoke() != Dp.Unspecified) {
                maxHeight.invoke().roundToPx().coerceAtLeast(0)
            } else {
                Constraints.Infinity
            }
            val minWidth = if (minWidth.invoke() != Dp.Unspecified) {
                minWidth.invoke().roundToPx().coerceAtMost(maxWidth).coerceAtLeast(0).let {
                    if (it != Constraints.Infinity) it else 0
                }
            } else {
                0
            }
            val minHeight = if (minHeight.invoke() != Dp.Unspecified) {
                minHeight.invoke().roundToPx().coerceAtMost(maxHeight).coerceAtLeast(0).let {
                    if (it != Constraints.Infinity) it else 0
                }
            } else {
                0
            }
            return Constraints(
                minWidth = minWidth,
                minHeight = minHeight,
                maxWidth = maxWidth,
                maxHeight = maxHeight
            )
        }

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val wrappedConstraints = targetConstraints.let { targetConstraints ->
            if (enforceIncoming) {
                constraints.constrain(targetConstraints)
            } else {
                val resolvedMinWidth = if (minWidth.invoke() != Dp.Unspecified) {
                    targetConstraints.minWidth
                } else {
                    constraints.minWidth.coerceAtMost(targetConstraints.maxWidth)
                }
                val resolvedMaxWidth = if (maxWidth.invoke() != Dp.Unspecified) {
                    targetConstraints.maxWidth
                } else {
                    constraints.maxWidth.coerceAtLeast(targetConstraints.minWidth)
                }
                val resolvedMinHeight = if (minHeight.invoke() != Dp.Unspecified) {
                    targetConstraints.minHeight
                } else {
                    constraints.minHeight.coerceAtMost(targetConstraints.maxHeight)
                }
                val resolvedMaxHeight = if (maxHeight.invoke() != Dp.Unspecified) {
                    targetConstraints.maxHeight
                } else {
                    constraints.maxHeight.coerceAtLeast(targetConstraints.minHeight)
                }
                Constraints(
                    resolvedMinWidth,
                    resolvedMaxWidth,
                    resolvedMinHeight,
                    resolvedMaxHeight
                )
            }
        }
        val placeable = measurable.measure(wrappedConstraints)
        return layout(placeable.width, placeable.height) {
            placeable.placeRelative(0, 0)
        }
    }

    override fun IntrinsicMeasureScope.minIntrinsicWidth(
        measurable: IntrinsicMeasurable,
        height: Int
    ): Int {
        val constraints = targetConstraints
        return if (constraints.hasFixedWidth) {
            constraints.maxWidth
        } else {
            constraints.constrainWidth(measurable.minIntrinsicWidth(height))
        }
    }

    override fun IntrinsicMeasureScope.minIntrinsicHeight(
        measurable: IntrinsicMeasurable,
        width: Int
    ): Int {
        val constraints = targetConstraints
        return if (constraints.hasFixedHeight) {
            constraints.maxHeight
        } else {
            constraints.constrainHeight(measurable.minIntrinsicHeight(width))
        }
    }

    override fun IntrinsicMeasureScope.maxIntrinsicWidth(
        measurable: IntrinsicMeasurable,
        height: Int
    ): Int {
        val constraints = targetConstraints
        return if (constraints.hasFixedWidth) {
            constraints.maxWidth
        } else {
            constraints.constrainWidth(measurable.maxIntrinsicWidth(height))
        }
    }

    override fun IntrinsicMeasureScope.maxIntrinsicHeight(
        measurable: IntrinsicMeasurable,
        width: Int
    ): Int {
        val constraints = targetConstraints
        return if (constraints.hasFixedHeight) {
            constraints.maxHeight
        } else {
            constraints.constrainHeight(measurable.maxIntrinsicHeight(width))
        }
    }
}