package com.fvalela.calendarjetpackcompose.date

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.Dp

@Composable
internal fun SingleDate(
    day: Int,
    modifier: Modifier = Modifier,
    onDayPressed: ((Long) -> Unit)?,
    dayInMilli: Long,
    dateBackgroundColour: Color,
    dateTextStyle: TextStyle,
    isOutOfRange: Boolean,
    circleDiameter: Dp,
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
                    MaterialTheme.colors.secondary.copy(alpha = 0.5f)
                else
                    MaterialTheme.colors.onBackground
            )
        } else {
            dateTextStyle
        }

    var dateModifier = modifier
        .clip(CircleShape)
        .background(backgroundColor)

    if (onDayPressed != null) {
        dateModifier = dateModifier.clickable {
            onDayPressed(dayInMilli)
        }
    }

    Box(
        modifier = dateModifier
            .layout { measurable, constraints ->
                val resolvedCircleDiameterInt = circleDiameter.toPx().toInt()
                    .coerceIn(constraints.minHeight, minOf(constraints.maxWidth, constraints.maxHeight))

                val placeable = measurable.measure(
                    constraints.copy(
                        minWidth = resolvedCircleDiameterInt,
                        maxWidth = resolvedCircleDiameterInt,
                        minHeight = resolvedCircleDiameterInt,
                        maxHeight = resolvedCircleDiameterInt,
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