package com.fvalela.calendarjetpackcompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

@Composable
internal fun DaysOfTheWeekRow(startDate: LocalDate, endDate: LocalDate) {

    var runningDate = startDate.minusDays(
        differenceBetweenTimeLibStartDayOfWeekAndPassedStartDayOfWeek(startDate).toLong())
    var runningCount = 0

    Row(modifier = Modifier.fillMaxWidth()) {
        while (runningDate.isBefore(endDate) && runningCount++ < lengthOfWeek) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, true),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = runningDate.dayOfWeek.getDisplayName(
                        TextStyle.NARROW,
                        Locale.getDefault()
                    )
                )
            }
            runningDate = runningDate.plusDays(1L)
        }
    }
}