package com.example.flo.compose.recomposition.modifier.modifier

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.InspectorValueInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.constrain
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import androidx.compose.ui.unit.dp

internal class StateSizeModifier(
    private val minWidth: State<Dp> = mutableStateOf(Dp.Unspecified),
    private val minHeight: State<Dp> = mutableStateOf(Dp.Unspecified),
    private val maxWidth: State<Dp> = mutableStateOf(Dp.Unspecified),
    private val maxHeight: State<Dp> = mutableStateOf(Dp.Unspecified),
    private val enforceIncoming: Boolean,
    inspectorInfo: InspectorInfo.() -> Unit,
) : LayoutModifier, InspectorValueInfo(inspectorInfo) {

    private val Density.targetConstraints: Constraints
        get() {
            val maxWidth = if (maxWidth.value != Dp.Unspecified) {
                maxWidth.value.coerceAtLeast(0.dp).roundToPx()
            } else {
                Constraints.Infinity
            }
            val maxHeight = if (maxHeight.value != Dp.Unspecified) {
                maxHeight.value.coerceAtLeast(0.dp).roundToPx()
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
        constraints: Constraints,
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
        height: Int,
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
        width: Int,
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
        height: Int,
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
        width: Int,
    ): Int {
        val constraints = targetConstraints
        return if (constraints.hasFixedHeight) {
            constraints.maxHeight
        } else {
            constraints.constrainHeight(measurable.maxIntrinsicHeight(width))
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is StateSizeModifier) return false
        return minWidth == other.minWidth &&
                minHeight == other.minHeight &&
                maxWidth == other.maxWidth &&
                maxHeight == other.maxHeight &&
                enforceIncoming == other.enforceIncoming
    }

    override fun hashCode() =
        (((((minWidth.hashCode() * 31 + minHeight.hashCode()) * 31) + maxWidth.hashCode()) * 31) + maxHeight.hashCode()) * 31
}

internal class LambdaSizeModifier(
    private val minWidth: () -> Dp = { Dp.Unspecified },
    private val minHeight: () -> Dp = { Dp.Unspecified },
    private val maxWidth: () -> Dp = { Dp.Unspecified },
    private val maxHeight: () -> Dp = { Dp.Unspecified },
    private val enforceIncoming: Boolean,
    inspectorInfo: InspectorInfo.() -> Unit,
) : LayoutModifier, InspectorValueInfo(inspectorInfo) {

    private val Density.targetConstraints: Constraints
        get() {
            val maxWidth = if (maxWidth.invoke() != Dp.Unspecified) {
                maxWidth.invoke().coerceAtLeast(0.dp).roundToPx()
            } else {
                Constraints.Infinity
            }
            val maxHeight = if (maxHeight.invoke() != Dp.Unspecified) {
                maxHeight.invoke().coerceAtLeast(0.dp).roundToPx()
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
        constraints: Constraints,
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
        height: Int,
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
        width: Int,
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
        height: Int,
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
        width: Int,
    ): Int {
        val constraints = targetConstraints
        return if (constraints.hasFixedHeight) {
            constraints.maxHeight
        } else {
            constraints.constrainHeight(measurable.maxIntrinsicHeight(width))
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is LambdaSizeModifier) return false
        return minWidth == other.minWidth &&
                minHeight == other.minHeight &&
                maxWidth == other.maxWidth &&
                maxHeight == other.maxHeight &&
                enforceIncoming == other.enforceIncoming
    }

    override fun hashCode() = (((((minWidth.hashCode() * 31 + minHeight.hashCode()) * 31) + maxWidth.hashCode()) * 31) + maxHeight.hashCode()) * 31
}
