package com.fvalela.calendarjetpackcompose.date

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
internal fun SingleDate(
    day: Int,
    modifier: Modifier = Modifier,
    onDayPressed: (Long) -> Unit,
    dayInMilli: Long,
    dateBackgroundColour: Color,
    dateTextStyle: TextStyle,
    isOutOfRange: Boolean = false
) {
    val backgroundColor =
        when (dateBackgroundColour) {
            Color.Unspecified -> MaterialTheme.colors.background
            else -> dateBackgroundColour
        }

    val textStyle: TextStyle =
        if (dateTextStyle == TextStyle.Default) {
            MaterialTheme.typography.body1.copy(color =
                if (isOutOfRange)
                    MaterialTheme.colors.secondary.copy(alpha = 0.75f)
                else
                    MaterialTheme.colors.onBackground
            )
        } else {
            dateTextStyle
        }

    Box(
        modifier = modifier
            .padding(1.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable {
                onDayPressed(dayInMilli)
            }
            .layout { measurable, constraints ->
                val boxSize = minOf(constraints.maxWidth, constraints.maxHeight)

                val placeable = measurable.measure(
                    constraints.copy(
                        minWidth = boxSize,
                        maxWidth = boxSize,
                        minHeight = boxSize,
                        maxHeight = boxSize,
                    )
                )

                layout(placeable.width, placeable.width) {
                    placeable.place(x = 0, y = 0, zIndex = 0f)
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier,
            text = day.toString(),
            style = textStyle,
        )
    }
}