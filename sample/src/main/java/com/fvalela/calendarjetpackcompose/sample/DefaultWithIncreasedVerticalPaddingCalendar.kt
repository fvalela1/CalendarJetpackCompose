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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.fvalela.calendarjetpackcompose.CalendarJetpackCompose
import com.fvalela.calendarjetpackcompose.sample.ui.CalendarJetpackComposeSampleTheme
import com.fvalela.calendarjetpackcompose.sample.viewmodel.CalendarViewModel

class DefaultWithIncreasedVerticalPaddingCalendar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CalendarJetpackComposeSampleTheme(darkTheme = false) {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Column(Modifier.fillMaxSize()) {
                        DefaultWithIncreasedVerticalPaddingCalendarHelper()
                    }
                }
            }
        }
    }
}

@Composable
private fun DefaultWithIncreasedVerticalPaddingCalendarHelper() {
    CalendarJetpackCompose(
        verticalPadding = 10.dp
    )
}