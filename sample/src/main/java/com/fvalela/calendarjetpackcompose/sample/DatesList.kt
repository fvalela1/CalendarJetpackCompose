package com.fvalela.calendarjetpackcompose.sample

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fvalela.calendarjetpackcompose.sample.model.SelectedDate
import com.fvalela.calendarjetpackcompose.sample.viewmodel.CalendarViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

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
private fun DatesListItem(dateItem: Long) {
    val date: LocalDate =
        Instant.ofEpochMilli(dateItem).atZone(ZoneId.systemDefault()).toLocalDate()
    Text(text = date.toString(),
        style = MaterialTheme.typography.body2,
        modifier = Modifier.padding(vertical = 5.dp),
        color = MaterialTheme.colors.onBackground)
}