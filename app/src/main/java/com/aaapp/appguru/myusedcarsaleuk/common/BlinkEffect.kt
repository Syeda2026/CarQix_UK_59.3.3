package com.aaapp.appguru.myusedcarsaleuk.common

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import kotlinx.coroutines.launch

// Soft, lighter highlight colors for smooth blink transition
private val LightCardHighlight = Color(0xFF3B82F6)
private val LightButtonHighlight = Color(0xFFFFFFFF)

/**
 * Modifier extension that smoothly changes to a lighter highlight color
 * and fades back to normal over 200ms before invoking [onClick].
 */
fun Modifier.blinkClickable(
    enabled: Boolean = true,
    totalDurationMillis: Int = 200,
    onClick: () -> Unit
): Modifier = composed {
    val coroutineScope = rememberCoroutineScope()
    val highlightAnim = remember { Animatable(0f) }
    var isAnimating by remember { mutableStateOf(false) }

    this
        .drawWithContent {
            drawContent()
            if (highlightAnim.value > 0f) {
                drawRect(color = LightCardHighlight.copy(alpha = 0.16f * highlightAnim.value))
            }
        }
        .clickable(
            enabled = enabled,
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple()
        ) {
            if (!isAnimating && enabled) {
                isAnimating = true
                coroutineScope.launch {
                    try {
                        val halfDuration = totalDurationMillis / 2
                        highlightAnim.animateTo(1f, tween(halfDuration, easing = FastOutSlowInEasing))
                        highlightAnim.animateTo(0f, tween(halfDuration, easing = FastOutSlowInEasing))
                    } finally {
                        highlightAnim.snapTo(0f)
                        isAnimating = false
                        onClick()
                    }
                }
            }
        }
}

/**
 * Card with smooth light color fade animation (200ms) before triggering [onClick].
 */
@Composable
fun BlinkCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(),
    elevation: CardElevation = CardDefaults.cardElevation(),
    border: BorderStroke? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val highlightAnim = remember { Animatable(0f) }
    var isAnimating by remember { mutableStateOf(false) }

    Card(
        onClick = {
            if (!isAnimating && enabled) {
                isAnimating = true
                coroutineScope.launch {
                    try {
                        highlightAnim.animateTo(1f, tween(100, easing = FastOutSlowInEasing))
                        highlightAnim.animateTo(0f, tween(100, easing = FastOutSlowInEasing))
                    } finally {
                        highlightAnim.snapTo(0f)
                        isAnimating = false
                        onClick()
                    }
                }
            }
        },
        modifier = modifier.drawWithContent {
            drawContent()
            if (highlightAnim.value > 0f) {
                drawRect(color = LightCardHighlight.copy(alpha = 0.16f * highlightAnim.value))
            }
        },
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        content = content
    )
}

/**
 * Button with smooth light color fade animation (200ms) before triggering [onClick].
 */
@Composable
fun BlinkButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val highlightAnim = remember { Animatable(0f) }
    var isAnimating by remember { mutableStateOf(false) }

    Button(
        onClick = {
            if (!isAnimating && enabled) {
                isAnimating = true
                coroutineScope.launch {
                    try {
                        highlightAnim.animateTo(1f, tween(100, easing = FastOutSlowInEasing))
                        highlightAnim.animateTo(0f, tween(100, easing = FastOutSlowInEasing))
                    } finally {
                        highlightAnim.snapTo(0f)
                        isAnimating = false
                        onClick()
                    }
                }
            }
        },
        modifier = modifier.drawWithContent {
            drawContent()
            if (highlightAnim.value > 0f) {
                drawRect(color = LightButtonHighlight.copy(alpha = 0.22f * highlightAnim.value))
            }
        },
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        content = content
    )
}

/**
 * OutlinedButton with smooth light color fade animation (200ms) before triggering [onClick].
 */
@Composable
fun BlinkOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.outlinedShape,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = ButtonDefaults.outlinedButtonBorder(enabled),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val highlightAnim = remember { Animatable(0f) }
    var isAnimating by remember { mutableStateOf(false) }

    OutlinedButton(
        onClick = {
            if (!isAnimating && enabled) {
                isAnimating = true
                coroutineScope.launch {
                    try {
                        highlightAnim.animateTo(1f, tween(100, easing = FastOutSlowInEasing))
                        highlightAnim.animateTo(0f, tween(100, easing = FastOutSlowInEasing))
                    } finally {
                        highlightAnim.snapTo(0f)
                        isAnimating = false
                        onClick()
                    }
                }
            }
        },
        modifier = modifier.drawWithContent {
            drawContent()
            if (highlightAnim.value > 0f) {
                drawRect(color = LightCardHighlight.copy(alpha = 0.16f * highlightAnim.value))
            }
        },
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        content = content
    )
}

/**
 * TextButton with smooth light color fade animation (200ms) before triggering [onClick].
 */
@Composable
fun BlinkTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.textShape,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val highlightAnim = remember { Animatable(0f) }
    var isAnimating by remember { mutableStateOf(false) }

    TextButton(
        onClick = {
            if (!isAnimating && enabled) {
                isAnimating = true
                coroutineScope.launch {
                    try {
                        highlightAnim.animateTo(1f, tween(100, easing = FastOutSlowInEasing))
                        highlightAnim.animateTo(0f, tween(100, easing = FastOutSlowInEasing))
                    } finally {
                        highlightAnim.snapTo(0f)
                        isAnimating = false
                        onClick()
                    }
                }
            }
        },
        modifier = modifier.drawWithContent {
            drawContent()
            if (highlightAnim.value > 0f) {
                drawRect(color = LightCardHighlight.copy(alpha = 0.16f * highlightAnim.value))
            }
        },
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        content = content
    )
}

/**
 * IconButton with smooth light color fade animation (200ms) before triggering [onClick].
 */
@Composable
fun BlinkIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val highlightAnim = remember { Animatable(0f) }
    var isAnimating by remember { mutableStateOf(false) }

    IconButton(
        onClick = {
            if (!isAnimating && enabled) {
                isAnimating = true
                coroutineScope.launch {
                    try {
                        highlightAnim.animateTo(1f, tween(100, easing = FastOutSlowInEasing))
                        highlightAnim.animateTo(0f, tween(100, easing = FastOutSlowInEasing))
                    } finally {
                        highlightAnim.snapTo(0f)
                        isAnimating = false
                        onClick()
                    }
                }
            }
        },
        modifier = modifier.drawWithContent {
            drawContent()
            if (highlightAnim.value > 0f) {
                drawRect(color = LightCardHighlight.copy(alpha = 0.16f * highlightAnim.value))
            }
        },
        enabled = enabled,
        colors = colors,
        content = content
    )
}
