package com.fvalela.calendarjetpackcompose.sample.util

import androidx.compose.ui.graphics.Color

object ColourPicker {
    var currentIndex = 0
    private val ColourList = listOf(
        Pair(Color.Red, Color.White),
        Pair(Color.Blue, Color.White),
        Pair(Color.Cyan, Color.Black),
        Pair(Color.Yellow, Color.Black),
        Pair(Color.Magenta, Color.Black),
    )

    fun getColours(): Pair<Color, Color> = ColourList[currentIndex++ % ColourList.size]
}