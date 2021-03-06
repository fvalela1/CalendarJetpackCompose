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
import androidx.lifecycle.ViewModelProvider
import com.fvalela.calendarjetpackcompose.CalendarJetpackCompose
import com.fvalela.calendarjetpackcompose.sample.ui.CalendarJetpackComposeSampleTheme
import com.fvalela.calendarjetpackcompose.sample.viewmodel.CalendarViewModel

class CustomMonthNavArrowsCalendar : AppCompatActivity() {
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
                        CustomMonthNavArrowsCalendarHelper(viewModel = viewModel)
                        DatesList(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
private fun CustomMonthNavArrowsCalendarHelper(viewModel: CalendarViewModel) {
    val month: Int by viewModel.selectedMonth.observeAsState(initial = 0)
    val year: Int by viewModel.selectedYear.observeAsState(initial = 0)

    CalendarJetpackCompose(
        year = year,
        month = month,
        onNavigateMonthPressed = viewModel::updateSelectedMonth,
        canNavigateMonths = true,
        navigateMonthDrawableIds = Pair(R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground),
    )
}