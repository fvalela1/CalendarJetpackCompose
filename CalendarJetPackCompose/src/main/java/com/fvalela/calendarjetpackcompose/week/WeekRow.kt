package com.fvalela.calendarjetpackcompose.week

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.fvalela.calendarjetpackcompose.DAY_IN_MILLI
import com.fvalela.calendarjetpackcompose.date.SingleDate
import com.fvalela.calendarjetpackcompose.lengthOfWeek
import com.fvalela.calendarjetpackcompose.differenceBetweenTimeLibEndDayOfWeekAndPassedEndDayOfWeek
import com.fvalela.calendarjetpackcompose.differenceBetweenTimeLibStartDayOfWeekAndPassedStartDayOfWeek
import com.fvalela.calendarjetpackcompose.model.CalendarDate
import com.fvalela.calendarjetpackcompose.ui.DateTextStyle
import java.time.LocalDate
import java.time.ZoneId

@Composable
internal fun WeekRows(
    startDate: LocalDate,
    endDate: LocalDate,
    onDayPressed: ((Long) -> Unit)?,
    verticalPadding: Dp,
    selectedDates: Collection<CalendarDate>,
    dateCircleDiameter: Dp,
) {
    // todo (fvalela - issue#): add functionality to start and end week at specific weekdays
    //    val startWeekDay = startDate.dayOfWeek.value
    //    val endWeekDay = endDate.dayOfWeek.value

    val nbDaysPriorToStartDateToShow = differenceBetweenTimeLibStartDayOfWeekAndPassedStartDayOfWeek(startDate)
    val nbDaysAfterEndDateToShow = differenceBetweenTimeLibEndDayOfWeekAndPassedEndDayOfWeek(endDate)

    var currentWorkingDate = startDate.minusDays(nbDaysPriorToStartDateToShow.toLong())

    while (currentWorkingDate.isBefore(endDate.plusDays(nbDaysAfterEndDateToShow.toLong()))) {
        when {
            // date is before set starting date
            currentWorkingDate.isBefore(startDate) ->
                WeekRow(
                    weekStartDate = currentWorkingDate,
                    absoluteStartDate = startDate,
                    absoluteEndDate = endDate,
                    verticalPadding = verticalPadding,
                    onDayPressed = onDayPressed,
                    selectedDates = selectedDates,
                    dateCircleDiameter = dateCircleDiameter,
                )
            // date is after set end date
            currentWorkingDate.isAfter(endDate.minusDays(6)) ->
                WeekRow(
                    weekStartDate = currentWorkingDate,
                    absoluteEndDate = endDate,
                    verticalPadding = verticalPadding,
                    onDayPressed = onDayPressed,
                    selectedDates = selectedDates,
                    dateCircleDiameter = dateCircleDiameter,
                )
            // date is inbetween set start date and set end date
            currentWorkingDate.isAfter(startDate.minusDays(1)) && currentWorkingDate.isBefore(endDate.plusDays(1)) ->
                WeekRow(
                    weekStartDate = currentWorkingDate,
                    verticalPadding = verticalPadding,
                    onDayPressed = onDayPressed,
                    selectedDates = selectedDates,
                    dateCircleDiameter = dateCircleDiameter,
                )
        }
        currentWorkingDate = currentWorkingDate.plusDays(lengthOfWeek.toLong())
    }
}

@Composable
private fun WeekRow(
    weekStartDate: LocalDate,
    absoluteStartDate: LocalDate = LocalDate.MIN,
    absoluteEndDate: LocalDate = LocalDate.MAX,
    verticalPadding: Dp,
    selectedDates: Collection<CalendarDate>,
    onDayPressed: ((Long) -> Unit)?,
    dateCircleDiameter: Dp,
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(verticalPadding)) {
        var runningDate = weekStartDate

        for (day in 0 until lengthOfWeek) {
            val runningDateEpochMilli =
                runningDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            val isOutOfRange = runningDate.isBefore(absoluteStartDate) ||
                    runningDate.isAfter(absoluteEndDate)
            val dateBackgroundColourAndTextStyle = getSelectedDateColoursAndTextStyle(selectedDates, runningDateEpochMilli, isOutOfRange)
            SingleDate(
                modifier = Modifier.weight(1f, true),
                day = runningDate.dayOfMonth,
                dateBackgroundColour = dateBackgroundColourAndTextStyle.first,
                dateTextStyle = dateBackgroundColourAndTextStyle.second,
                onDayPressed = onDayPressed,
                dayInMilli = runningDateEpochMilli,
                isOutOfRange = isOutOfRange,
                circleDiameter = dateCircleDiameter,
            )
            runningDate = runningDate.plusDays(1L)
        }
    }
}


private fun getSelectedDateColoursAndTextStyle(
    selectedDates: Collection<CalendarDate>,
    dateStartTimeInEpochMilli: Long,
    isOutOfRange: Boolean
): Pair<Color, TextStyle> {
    val dateEndTimeInEpochMilli = dateStartTimeInEpochMilli + DAY_IN_MILLI
    for (date in selectedDates) {
        if (date.dateInMilli in dateStartTimeInEpochMilli until dateEndTimeInEpochMilli) {
            val resolvedTextStyle = resolveTextStyle(date.textStyle)
            val resolvedBackgroundColour = resolveBackgroundColour(date.backgroundColour, isOutOfRange)
            return Pair(
                resolvedBackgroundColour,
                resolvedTextStyle
            )
        }
    }
    return Pair(Color.Unspecified, TextStyle.Default)
}

private fun resolveBackgroundColour(backgroundColour: Color, isOutOfRange: Boolean): Color {
    return if (isOutOfRange) {
        backgroundColour.copy(alpha = 0.5f)
    } else {
        backgroundColour
    }
}

private fun resolveTextStyle(textStyle: TextStyle?): TextStyle {
    return textStyle ?: DateTextStyle.selected

}
