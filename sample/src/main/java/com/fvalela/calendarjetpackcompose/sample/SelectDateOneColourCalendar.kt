package com.fvalela.calendarjetpackcompose.sample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.fvalela.calendarjetpackcompose.CalendarJetpackCompose
import com.fvalela.calendarjetpackcompose.model.CalendarDate
import com.fvalela.calendarjetpackcompose.sample.model.SelectedDate
import com.fvalela.calendarjetpackcompose.sample.ui.CalendarJetpackComposeSampleTheme
import com.fvalela.calendarjetpackcompose.sample.ui.blue200
import com.fvalela.calendarjetpackcompose.sample.viewmodel.CalendarViewModel

class SelectDateOneColourCalendar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this).get(CalendarViewModel::class.java)

        setContent {
            CalendarJetpackComposeSampleTheme(darkTheme = false) {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Column(Modifier.fillMaxSize()) {
                        SelectDateOneColourCalendarHelper(viewModel = viewModel)
                        DatesList(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
private fun SelectDateOneColourCalendarHelper(viewModel: CalendarViewModel) {
    val month: Int by viewModel.selectedMonth.observeAsState(initial = 0)
    val year: Int by viewModel.selectedYear.observeAsState(initial = 0)
    val selectedDates: Set<SelectedDate> by viewModel.selectedDates.observeAsState(initial = mutableSetOf())

    CalendarJetpackCompose(
        year = year,
        month = month,
        selectedDates = convertSelectedDatesToCalendarDates(dates = selectedDates),
        onDayPressed = viewModel::updateSelectedDate,
        onNavigateMonthPressed = viewModel::updateSelectedMonth,
        canNavigateMonths = true,
    )
}

private fun convertSelectedDatesToCalendarDates(dates: Set<SelectedDate>): Set<CalendarDate> {
    val convertedDates: MutableSet<CalendarDate> = mutableSetOf()

    dates.forEach {
        convertedDates.add(CalendarDate(it.dateInMilli, blue200, TextStyle.Default))
    }

    return convertedDates
}