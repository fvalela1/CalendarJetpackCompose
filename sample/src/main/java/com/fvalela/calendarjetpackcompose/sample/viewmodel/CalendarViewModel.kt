package com.fvalela.calendarjetpackcompose.sample.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fvalela.calendarjetpackcompose.sample.model.SelectedDate
import com.fvalela.calendarjetpackcompose.sample.util.ColourPicker

class CalendarViewModel : ViewModel() {

    private val _selectedDates: MutableLiveData<Set<SelectedDate>> = MutableLiveData(setOf())
    val selectedDates: LiveData<Set<SelectedDate>>
        get() = _selectedDates

    private var _selectedMonth: MutableLiveData<Int> = MutableLiveData(3)
    val selectedMonth: LiveData<Int>
        get() = _selectedMonth

    private var _selectedYear: MutableLiveData<Int> = MutableLiveData(2021)
    val selectedYear: LiveData<Int>
        get() = _selectedYear

    fun updateSelectedDate(newDate: Long) {
        Log.d("[fvalela]", "add date: $newDate")

        _selectedDates.value = if (_selectedDates.value?.any { date -> date.dateInMilli == newDate } == true) {
            _selectedDates.value?.filter { date ->
                date.dateInMilli != newDate
            }?.toSet()
        } else {
            val colours = ColourPicker.getColours()
            _selectedDates.value?.plus(SelectedDate(newDate, colours.first, colours.second))
        }
    }

    fun updateSelectedMonth(newMonth: Int, newYear: Int) {
        updateSelectedYear(newYear)
        _selectedMonth.value = newMonth
    }

    private fun updateSelectedYear(newYear: Int) {
        _selectedYear.value = newYear
    }
}
