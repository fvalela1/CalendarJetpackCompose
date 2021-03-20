<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
  </ol>
</details>

# CalendarJetpackCompose
A Jetpack Compose Calendar library to easily add calendar functionality to your Android app.

## TODO
- add issues in github
- add github issues to specific lines of code
- create release tag
- add to jitpack

## Getting Started
### Prerequisites

In order to be able to use `CalendarJetpackCompose`, it is required to configure `Jetpack`. Follow the steps in [this](https://developer.android.com/jetpack/compose/setup) tutorial to configure your dev environment accordingly.

### Installation

1. Clone the repo
    
    **HTTPS**
    ```sh
   git clone https://github.com/fvalela1/CalendarJetpackCompose.git
   ```
   **SSH**
   ```sh
   git clone git@github.com:fvalela1/CalendarJetpackCompose.git
   ```
2. Open Android Studio and add `CalendarJetpackCompose` to your project
3. **TODO** Configure the next steps
## Usage

**Single Month Calendar**
<table>
<tr><td> Source </td> <td> Result </td></tr>
<tr>
<td>

```kotlin
CalendarJetpackCompose() // defaults to your current year + month
```

sample: [SingleMonthCalendar.kt](https://github.com/fvalela1/CalendarJetpackCompose/blob/main/sample/src/main/java/com/fvalela/calendarjetpackcompose/sample/SingleMonthCalendar.kt)

</td>
<td>

<img width="250" alt="Screenshot" src="docs/images/Default_Calendar.png">

</td>
</tr>
</table>

**Default Calendar With Vertical Padding**
<table>
<tr><td> Source </td> <td> Result </td></tr>
<tr>
<td>

```kotlin
CalendarJetpackCompose(
    verticalPadding = 10.dp
)
```

sample: [DefaultWithIncreasedVerticalPaddingCalendar.kt](https://github.com/fvalela1/CalendarJetpackCompose/blob/main/sample/src/main/java/com/fvalela/calendarjetpackcompose/sample/DefaultWithIncreasedVerticalPaddingCalendar.kt)

</td>
<td>

<img width="250" alt="Screenshot" src="docs/images/Default_Calendar_Vertical_Padding.png">

</td>
</tr>
</table>


**Default Calendar With Month Navigation**
<table>
<tr><td> Source </td> <td> Result </td></tr>
<tr>
<td>

```kotlin
CalendarJetpackCompose(
    canNavigateMonths = true, // true if month nav arrows are visible or not
    onNavigateMonthPressed = viewModel::updateSelectedMonth, // function to trigger when a month navigation arrow is pressed
    year = year, // year in view
    month = month, // month in view
)
```

sample: [DefaultWithMonthNavigationCalendar.kt](https://github.com/fvalela1/CalendarJetpackCompose/blob/main/sample/src/main/java/com/fvalela/calendarjetpackcompose/sample/DefaultWithMonthNavigationCalendar.kt)

</td>
<td>

<img width="350" alt="Screenshot" src="docs/images/Default_Calendar_Month_Navigation.gif">

</td>
</tr>
</table>

**Custom Month Navigation Arrows** 
<table>
<tr><td> Source </td> <td> Result </td></tr>
<tr>
<td>

```kotlin
CalendarJetpackCompose(
    year = year,
    month = month,
    onNavigateMonthPressed = viewModel::updateSelectedMonth,
    canNavigateMonths = true,
    navigateMonthDrawableIds = Pair(R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground), // add in your own drawables to replace the month nav arrows
)
```

sample: [CustomMonthNavArrowsCalendar.kt](https://github.com/fvalela1/CalendarJetpackCompose/blob/main/sample/src/main/java/com/fvalela/calendarjetpackcompose/sample/CustomMonthNavArrowsCalendar.kt)

</td>
<td>

<img width="350" alt="Screenshot" src="docs/images/Custom_Nav_Buttons.gif">

</td>
</tr>
</table>

### Selected Dates

The library has a model - [CalendarDate.kt](https://github.com/fvalela1/CalendarJetpackCompose/blob/main/CalendarJetPackCompose/src/main/java/com/fvalela/calendarjetpackcompose/model/CalendarDate.kt) - that you can use to customize the selected dates. 
The model takes in three parameters: 
* `dateInMilli: Long` - the date in milliseconds from epoch.
* `backgroundColour: Color` - the colour of the background when selected.
* `textStyle: TextStyle?` - the style of the number (i.e. bold, font, etc...) when it is selected.

The below samples are a showcase of using either a single colour or multiple colours as backgrounds for the selected dates.

`convertSelectedDatesToCalendarDates()` can be found in any of the samples.



**Select Date with One Colour**
<table>
<tr><td> Source </td> <td> Result </td></tr>
<tr>
<td>

```kotlin
CalendarJetpackCompose(
    year = year,
    month = month,
    selectedDates = convertSelectedDatesToCalendarDates(dates = selectedDates),
    onDayPressed = viewModel::updateSelectedDate, // function to trigger when a day is pressed. It takes in a long (date in milliseconds from epoch)
    onNavigateMonthPressed = viewModel::updateSelectedMonth,
    canNavigateMonths = true,
)
```

sample: [SelectDateOneColourCalendar.kt](https://github.com/fvalela1/CalendarJetpackCompose/blob/main/sample/src/main/java/com/fvalela/calendarjetpackcompose/sample/SelectDateOneColourCalendar.kt)

</td>
<td>

<img width="350" alt="Screenshot" src="docs/images/Single_Colour_Date_Selection.gif">

</td>
</tr>
</table>

**Select Date with Multiple Colours + Padding**

<img src="https://user-images.githubusercontent.com/12566260/111799485-4a651380-8888-11eb-94fc-352b45ca085b.gif" width="205"/>

sample: [SelectDateMultiColourCalendar.kt](https://github.com/fvalela1/CalendarJetpackCompose/blob/main/sample/src/main/java/com/fvalela/calendarjetpackcompose/sample/SelectDateMultiColourCalendar.kt)

