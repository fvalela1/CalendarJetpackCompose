package com.fvalela.calendarjetpackcompose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fvalela.calendarjetpackcompose.model.CalendarDate
import com.fvalela.calendarjetpackcompose.month.MonthRow
import com.fvalela.calendarjetpackcompose.week.WeekRows
import java.time.Instant
import java.time.LocalDate

var lengthOfWeek: Int = 0

@Composable
fun CalendarJetpackCompose(
    year: Int = 0,
    month: Int = 0,
//    onDayPressed: (Long) -> Unit = { /*no op*/ }, // todo (fvalela - issue#): allow to pass in null
    onDayPressed: ((Long) -> Unit)? = null,
    onNavigateMonthPressed: (Int, Int) -> Unit = { _, _ -> /*no op*/ },
    canNavigateMonths: Boolean = false,
    useMonthShortName: Boolean = true,
    selectedDates: Collection<CalendarDate> = setOf(),
    navigateMonthDrawableIds: Pair<Int, Int> =
        Pair(
            R.drawable.ic_baseline_keyboard_arrow_left_24,
            R.drawable.ic_baseline_keyboard_arrow_right_24
        ),
    verticalPadding: Dp = 0.dp,
    dateCircleDiameter: Dp = 42.dp
    // todo (fvalela - issue#): allow user to adjust what start and end day is (weekly view, for example)
    //    startDay: Int = 1,
    //    endDay: Int = 100,
) {
    val startDay = 1
    val endDay = 31

    val today = LocalDate.now()
    val resolvedMonth = if (month < 1 || month > 12) today.monthValue else month
    val resolvedYear = if (month < 1 || year < 1) today.year else year

    val lengthOfCurrentMonth = LocalDate.of(resolvedYear, resolvedMonth, 1).lengthOfMonth()

    val resolvedStartDate = resolveLocalDate(
        resolvedYear,
        resolvedMonth,
        maybeResolveDay(startDay, lengthOfCurrentMonth, false)
    )

    val resolvedEndDate = resolveLocalDate(
        resolvedYear,
        resolvedMonth,
        maybeResolveDay(endDay, lengthOfCurrentMonth, true)
    )
    assert(resolvedStartDate.isBefore(resolvedEndDate))

    lengthOfWeek = calculateLengthOfWeek(resolvedStartDate, resolvedEndDate)

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        YearRow(
            year = resolvedYear,
            verticalPadding = verticalPadding,
        )

        MonthRow(
            year = resolvedYear,
            month = resolvedMonth,
            canNavigateMonths = canNavigateMonths,
            navigateMonthDrawableIds = navigateMonthDrawableIds,
            onNavigateMonthPressed = onNavigateMonthPressed,
            useMonthShortName = useMonthShortName,
            verticalPadding = verticalPadding,
        )

        DaysOfTheWeekRow(
            startDate = resolvedStartDate,
            endDate = resolvedEndDate,
        )

        DividerBetweenDaysOfWeekAndWeekRow(
            verticalPadding = verticalPadding
        )

        WeekRows(
            startDate = resolvedStartDate,
            endDate = resolvedEndDate,
            onDayPressed = onDayPressed,
            verticalPadding = verticalPadding,
            selectedDates = selectedDates,
            dateCircleDiameter = dateCircleDiameter,
        )
    }
}

private fun maybeResolveDay(
    unresolvedDay: Int,
    lengthOfCurrentMonth: Int,
    isEndDate: Boolean = false,
): Int {
    if (unresolvedDay < 1 || unresolvedDay > lengthOfCurrentMonth) {
        return if (isEndDate) lengthOfCurrentMonth else 1
    }
    return unresolvedDay
}

@Composable
private fun DividerBetweenDaysOfWeekAndWeekRow(color: Color = MaterialTheme.colors.onBackground, thickness: Dp = 1.dp, verticalPadding: Dp) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(verticalPadding)
    ) {
        Divider(color = color, thickness = thickness)
    }
}


@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {

    val datePressed = remember { mutableStateOf(0L) }
    CalendarJetpackCompose(
        month = 3,
        year = 2021,
        useMonthShortName = true,
        selectedDates = listOf(CalendarDate(Instant.now().toEpochMilli(), Color.Blue)),
        onDayPressed = { newDayPressed ->
            // i.e. update viewModel
            datePressed.value = newDayPressed
        },
        canNavigateMonths = true,
        verticalPadding = 5.dp
    )
}
