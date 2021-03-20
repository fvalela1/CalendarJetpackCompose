package com.fvalela.calendarjetpackcompose.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

data class CalendarDate(val dateInMilli: Long, val backgroundColour: Color = Color.Unspecified, val textStyle: TextStyle? = null)
