package org.catsproject.project.ui.view


import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import kotlinx.coroutines.delay
import org.catsproject.project.core.di.isDesktop
import kotlin.math.*

enum class PullRefreshState {
    Idle,
    Pulling,
    Refreshing,
    Success
}

@Composable
fun PullToRefreshContainer(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    refreshThreshold: Float = 80f,
    content: @Composable (LazyListState) -> Unit
) {
    val density = LocalDensity.current
    val listState = rememberLazyListState()

    var pullRefreshState by remember { mutableStateOf(PullRefreshState.Idle) }
    var dragOffset by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }

    val refreshTriggerPx = with(density) { refreshThreshold.dp.toPx() }


    val offsetAnimation = animateFloatAsState(
        targetValue = when {
            isRefreshing -> refreshTriggerPx
            isDragging -> dragOffset
            else -> 0f
        },
        animationSpec = tween(
            durationMillis = if (isDragging) 0 else 300,
            easing = EaseOutCubic
        )
    )

    val isDesktop = isDesktop()

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (listState.firstVisibleItemIndex == 0 &&
                    listState.firstVisibleItemScrollOffset == 0 &&
                    available.y > 0f
                ) {
                    dragOffset += available.y
                    pullRefreshState = PullRefreshState.Pulling
                    return Offset(0f, available.y)
                }
                return Offset.Zero
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                if (dragOffset >= refreshTriggerPx && !isRefreshing) {
                    onRefresh()
                }
                dragOffset = 0f
                pullRefreshState = PullRefreshState.Idle
                return Velocity.Zero
            }
        }
    }


    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            pullRefreshState = PullRefreshState.Refreshing
        } else if (pullRefreshState == PullRefreshState.Refreshing) {
            pullRefreshState = PullRefreshState.Success
            delay(300)
            pullRefreshState = PullRefreshState.Idle
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .then(
                if (isDesktop) Modifier.pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            if (listState.firstVisibleItemIndex == 0 &&
                                listState.firstVisibleItemScrollOffset == 0
                            ) {
                                isDragging = true
                                pullRefreshState = PullRefreshState.Pulling
                            }
                        },
                        onDragEnd = {
                            if (isDragging) {
                                isDragging = false
                                if (dragOffset >= refreshTriggerPx && !isRefreshing) {
                                    onRefresh()
                                } else {
                                    pullRefreshState = PullRefreshState.Idle
                                }
                                dragOffset = 0f
                            }
                        },
                        onDrag = { change, dragAmount ->
                            if (isDragging) {
                                dragOffset = maxOf(0f, dragOffset + dragAmount.y)
                            }
                        }
                    )
                } else Modifier.nestedScroll(nestedScrollConnection)
            )

    ) {

        RefreshIndicator(
            state = pullRefreshState,
            progress = if (isDragging) (dragOffset / refreshTriggerPx).coerceIn(0f, 1f) else 0f,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset { IntOffset(0, (offsetAnimation.value - 40.dp.toPx()).roundToInt()) }
        )


        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(0, offsetAnimation.value.roundToInt()) }
        ) {
            content(listState)
        }
    }
}

@Composable
private fun RefreshIndicator(
    state: PullRefreshState,
    progress: Float,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val alpha by animateFloatAsState(
        targetValue = when (state) {
            PullRefreshState.Idle -> 0f
            PullRefreshState.Pulling -> progress
            PullRefreshState.Refreshing -> 1f
            PullRefreshState.Success -> 1f
        },
        animationSpec = tween(200)
    )

    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.White)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            PullRefreshState.Refreshing -> {
                Canvas(
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(rotation)
                ) {
                    drawRefreshSpinner(
                        color = Color.Red,
                        alpha = alpha
                    )
                }
            }
            PullRefreshState.Success -> {
                Canvas(
                    modifier = Modifier.size(24.dp)
                ) {
                    drawCheckmark(
                        color = Color.Red,
                        alpha = alpha
                    )
                }
            }
            else -> {
                Canvas(
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(progress * 180f)
                ) {
                    drawArrow(
                        color = Color.Red,
                        alpha = alpha
                    )
                }
            }
        }
    }
}

private fun DrawScope.drawRefreshSpinner(color: Color, alpha: Float) {
    val strokeWidth = 3.dp.toPx()
    val radius = (size.minDimension - strokeWidth) / 2

    drawArc(
        color = color.copy(alpha = alpha),
        startAngle = 0f,
        sweepAngle = 270f,
        useCenter = false,
        style = androidx.compose.ui.graphics.drawscope.Stroke(
            width = strokeWidth,
            cap = StrokeCap.Round
        )
    )
}

private fun DrawScope.drawCheckmark(color: Color, alpha: Float) {
    val strokeWidth = 3.dp.toPx()
    val path = androidx.compose.ui.graphics.Path()

    val centerX = size.width / 2
    val centerY = size.height / 2
    val checkSize = size.minDimension / 3

    path.moveTo(centerX - checkSize, centerY)
    path.lineTo(centerX - checkSize/3, centerY + checkSize/2)
    path.lineTo(centerX + checkSize, centerY - checkSize/2)

    drawPath(
        path = path,
        color = color.copy(alpha = alpha),
        style = androidx.compose.ui.graphics.drawscope.Stroke(
            width = strokeWidth,
            cap = StrokeCap.Round
        )
    )
}

private fun DrawScope.drawArrow(color: Color, alpha: Float) {
    val strokeWidth = 3.dp.toPx()
    val centerX = size.width / 2
    val centerY = size.height / 2
    val arrowSize = size.minDimension / 3

    // Línea principal
    drawLine(
        color = color.copy(alpha = alpha),
        start = Offset(centerX, centerY - arrowSize),
        end = Offset(centerX, centerY + arrowSize),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )

    // Punta de flecha
    drawLine(
        color = color.copy(alpha = alpha),
        start = Offset(centerX - arrowSize/2, centerY + arrowSize/2),
        end = Offset(centerX, centerY + arrowSize),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )

    drawLine(
        color = color.copy(alpha = alpha),
        start = Offset(centerX + arrowSize/2, centerY + arrowSize/2),
        end = Offset(centerX, centerY + arrowSize),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
}
