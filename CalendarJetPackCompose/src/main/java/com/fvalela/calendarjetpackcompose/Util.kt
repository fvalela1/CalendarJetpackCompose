package com.fvalela.calendarjetpackcompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import java.time.DayOfWeek
import java.time.LocalDate

internal const val DAY_IN_MILLI = 23 * 60 * 60 * 1000

internal fun resolveLocalDate(year: Int, month: Int, day: Int = 1): LocalDate {
    return LocalDate.of(year, month, day)
}

internal fun calculateLengthOfWeek(
    resolvedStartDate: LocalDate,
    resolvedEndDate: LocalDate,
): Int {
    return 7
    // todo (fvalela - issue#): allow library caller to configure how many days in a week
    //    val daysBetweenStartAndEndDate = resolvedStartDate.until(resolvedEndDate, ChronoUnit.DAYS)
    //    return if (daysBetweenStartAndEndDate < 7)
    //        daysBetweenStartAndEndDate.toInt()
    //    else
    //        7
}

// todo (fvalela - issue#): allow library caller to configure what
//  day of the week is the start (i.e. Sunday, Monday, Saturday, etc...)
internal fun differenceBetweenTimeLibEndDayOfWeekAndPassedEndDayOfWeek(endDate: LocalDate): Int {
    val endDateDayOfWeek = endDate.dayOfWeek.value
    return DayOfWeek.SATURDAY.value -
            if (endDateDayOfWeek == DayOfWeek.SUNDAY.value)
                0
            else
                endDateDayOfWeek
}

// todo (fvalela - issue#): allow library caller to configure what
//  day of the week is the start (i.e. Sunday, Monday, Saturday, etc...)
internal fun differenceBetweenTimeLibStartDayOfWeekAndPassedStartDayOfWeek(startDate: LocalDate): Int {
    val startDateDayOfWeek = startDate.dayOfWeek.value
    return if (startDateDayOfWeek == DayOfWeek.SUNDAY.value)
        0
    else
        startDateDayOfWeek
}

// custom layout to ensure each element - no matter the size -
// is placed evenly across the row, based off the largest elements width.
@Deprecated(message = "No longer necessary for this project but I don't have the heart to delete it")
@Composable
internal fun EachElementIsSizedAsLargestElementRow(
    modifier: Modifier = Modifier,
    nbOfElements: Int = 7,
    children: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = children
    ) { measurables, constraints ->

        // set variables
        var maxWidthOfElements = 0
        var maxHeightOfElements = 0

        // find the max width & height of children
        val placeables = measurables.mapIndexed { _, measurable ->
            val placeable = measurable.measure(constraints)

            maxWidthOfElements = maxOf(maxWidthOfElements, placeable.width)
            maxHeightOfElements = maxOf(maxHeightOfElements, placeable.height)

            placeable
        }

        // layout's width is the application's max width
        val layoutWidth = constraints.maxWidth

        // layout's height is tallest element coerced to the height constraints
        val layoutHeight = maxHeightOfElements
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        // calculate spaces & starting position
        val layoutWidthMinusElements = layoutWidth - (maxWidthOfElements * nbOfElements)
        val spaceBetweenEachElements = (layoutWidthMinusElements) / (nbOfElements) // only in between two elements need space
        val paddingAtBeginningOfRow = spaceBetweenEachElements / 2
        val startingXPosition = 0 + paddingAtBeginningOfRow
        val midOfMaxWidthOfElements = maxWidthOfElements / 2

        // set size of the parent layout + place children
        layout(layoutWidth, layoutHeight) {
            placeables.forEachIndexed { index, placeable ->
                val totalSizeOfPrecedingElements = index * maxWidthOfElements
                val totalSpaceBetweenPrecedingElements = index * spaceBetweenEachElements
                val midOfMaxElementMinusMidOfCurrentElement = midOfMaxWidthOfElements - (placeable.width / 2)

                placeable.placeRelative(
                    x = startingXPosition +
                            totalSizeOfPrecedingElements +
                            totalSpaceBetweenPrecedingElements +
                            midOfMaxElementMinusMidOfCurrentElement,
                    y = 0
                )
            }
        }
    }
}