package com.fvalela.calendarjetpackcompose.sample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.fvalela.calendarjetpackcompose.sample.Style.*
import com.fvalela.calendarjetpackcompose.sample.model.SelectedDate
import com.fvalela.calendarjetpackcompose.sample.ui.CalendarJetpackComposeDemoListTheme
import com.fvalela.calendarjetpackcompose.sample.ui.blue200
import com.fvalela.calendarjetpackcompose.sample.ui.typography
import com.fvalela.calendarjetpackcompose.CalendarJetpackCompose
import com.fvalela.calendarjetpackcompose.model.CalendarDate
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

enum class Style {
    DEFAULT,
    DEFAULT_WITH_VERTICAL_PADDING_10,
    CAN_SELECT_DATE_ONE_COLOUR,
    CAN_SELECT_DATE_MULTI_COLOUR,
    CAN_NAVIGATE_MONTH_CUSTOM_ARROWS,
    // todo (fvalela - issue#): not yet implemented
    //    WEEK_VIEW,
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this).get(CalendarViewModel::class.java)

        setContent {
            CalendarJetpackComposeDemoListTheme(darkTheme = false) {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Column(Modifier.fillMaxSize()) {
                        CalendarFactory(style = CAN_SELECT_DATE_MULTI_COLOUR, viewModel = viewModel)
                        DatesList(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
private fun CalendarFactory(@Suppress("SameParameterValue") style: Style = DEFAULT, viewModel: CalendarViewModel) {
    val month: Int by viewModel.selectedMonth.observeAsState(initial = 3)
    val year: Int by viewModel.selectedYear.observeAsState(initial = 0)
    val selectedDates: Set<SelectedDate> by viewModel.selectedDates.observeAsState(initial = mutableSetOf())
    when (style) {
        CAN_SELECT_DATE_ONE_COLOUR -> CalendarJetpackCompose(
            year = year,
            month = month,
            selectedDates = convertToCalendarDates(style = style, dates = selectedDates),
            onDayPressed = viewModel::updateSelectedDate,
            onNavigateMonthPressed = viewModel::updateSelectedMonth,
            canNavigateMonths = true,
            verticalPadding = 3.dp
        )
        CAN_NAVIGATE_MONTH_CUSTOM_ARROWS -> CalendarJetpackCompose(
            year = year,
            month = month,
            selectedDates = convertToCalendarDates(style = style, dates = selectedDates),
            onNavigateMonthPressed = viewModel::updateSelectedMonth,
            canNavigateMonths = true,
            navigateMonthDrawableIds = Pair(R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground),
        )
        CAN_SELECT_DATE_MULTI_COLOUR -> CalendarJetpackCompose(
            year = year,
            month = month,
            selectedDates = convertToCalendarDates(style = style, dates = selectedDates),
            onDayPressed = viewModel::updateSelectedDate,
            onNavigateMonthPressed = viewModel::updateSelectedMonth,
            canNavigateMonths = true,
        )
        DEFAULT_WITH_VERTICAL_PADDING_10 -> CalendarJetpackCompose(
            year = year,
            month = month,
            verticalPadding = 10.dp
        )
        else -> CalendarJetpackCompose(
            canNavigateMonths = true,
            onNavigateMonthPressed = viewModel::updateSelectedMonth,
            year = year,
            month = month,
        )
        // todo (fvalela - issue#): not yet implemented
        //        WEEK_VIEW -> Calendar(
        //            year = year,
        //            month = month,
        //            startDay = 1,
        //            endDay = 5,
        //            selectedDates = adaptToCalendarDates(style = style, dates = selectedDates),
        //            onDayPressed = viewModel::updateSelectedDate,
        //            onNavigateMonthPressed = viewModel::updateSelectedMonth,
        //            canNavigateMonths = true,
        //        )
    }
}

private fun convertToCalendarDates(style: Style = DEFAULT, dates: Set<SelectedDate>): Set<CalendarDate> {
    val convertedDates: MutableSet<CalendarDate> = mutableSetOf()

    when (style) {
        CAN_SELECT_DATE_ONE_COLOUR -> dates.forEach {
            convertedDates.add(CalendarDate(it.dateInMilli, blue200, TextStyle.Default))
        }
        CAN_SELECT_DATE_MULTI_COLOUR -> dates.forEach {
            convertedDates.add(CalendarDate(it.dateInMilli, it.backgroundColour, typography.body2.copy(color = it.textColour)))
        }
        CAN_NAVIGATE_MONTH_CUSTOM_ARROWS -> dates.forEach {
            convertedDates.add(CalendarDate(it.dateInMilli, it.backgroundColour, typography.body2.copy(color = it.textColour)))
        }
        // todo (fvalela - issue#): not yet implemented
        //        WEEK_VIEW -> dates.forEach {
        //            convertedDates.add(CalendarDate(it.dateInMilli, it.backgroundColour, typography.body2.copy(color = it.textColour)))
        //        }
        else -> dates.forEach {
            convertedDates.add(CalendarDate(it.dateInMilli, Color.Unspecified, TextStyle.Default))
        }
    }

    return convertedDates
}

@Composable
fun DatesList(
    viewModel: CalendarViewModel,
) {
    val selectedDates by viewModel.selectedDates.observeAsState(initial = mutableSetOf())
    val dateItems: List<SelectedDate> = selectedDates.toList()
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(vertical = 5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        items(items = dateItems) { date ->
            DatesListItem(dateItem = date.dateInMilli)
        }
    }
}

@Composable
fun DatesListItem(dateItem: Long) {
    val date: LocalDate =
        Instant.ofEpochMilli(dateItem).atZone(ZoneId.systemDefault()).toLocalDate()
    Text(text = date.toString(),
        style = MaterialTheme.typography.body2,
        modifier = Modifier.padding(vertical = 5.dp),
        color = MaterialTheme.colors.onBackground)
}
