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
import androidx.compose.ui.Modifier
import com.fvalela.calendarjetpackcompose.CalendarJetpackCompose
import com.fvalela.calendarjetpackcompose.sample.ui.CalendarJetpackComposeSampleTheme

class SingleMonthCalendar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CalendarJetpackComposeSampleTheme(darkTheme = false) {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Column(Modifier.fillMaxSize()) {
                        SingleMonthCalendarHelper()
                    }
                }
            }
        }
    }
}

@Composable
private fun SingleMonthCalendarHelper() {
    CalendarJetpackCompose()
}