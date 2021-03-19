# CalendarJetpackCompose
A Jetpack Compose Calendar library to easily add calendar functionality to your Android app.

## TODO
- document different use cases
- add issues in github
- add github issues to specific lines of code
- create release tag
- add to jitpack

## Samples

**Default Calendar**

<img width="205" alt="default_calendar" src="https://user-images.githubusercontent.com/12566260/111803752-9619bc00-888c-11eb-8bfc-e03622bcf7f1.png">

[link to sample]
```
CalendarJetpackCompose() // defaults to your current year + month
```

**Default Calendar With Vertical Padding**

<img src="https://user-images.githubusercontent.com/12566260/111799330-26a1cd80-8888-11eb-88f5-939f8e8833be.png" width="205"/>

[link to sample]
```
CalendarJetpackCompose(
    verticalPadding = 10.dp
)
```

**Default Calendar With Month Navigation**

<img src="https://user-images.githubusercontent.com/12566260/111799936-b47db880-8888-11eb-8a6f-1981fe88e26d.gif" width="205"/>

[link to sample]
```
CalendarJetpackCompose(
    canNavigateMonths = true, // true if month nav arrows are visible or not
    onNavigateMonthPressed = viewModel::updateSelectedMonth, // function to trigger when a month navigation arrow is pressed
    year = year, // year in view
    month = month, // month in view
)
```

**Custom Month Navigation Arrows** 

<img src="https://user-images.githubusercontent.com/12566260/111799533-56e96c00-8888-11eb-893e-d0985933d039.gif" width="205"/>

[link to sample]
```
CalendarJetpackCompose(
    year = year,
    month = month,
    onNavigateMonthPressed = viewModel::updateSelectedMonth,
    canNavigateMonths = true,
    navigateMonthDrawableIds = Pair(R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground), // add in your own drawables to replace the month nav arrows
)
```

### Selected Dates

The library has a model `CalendarDate` [link]() that you can use to customize the selected dates. 
The model includes 
* `dateInMilli: Long` - the date in milliseconds from epoch.
* `backgroundColour: Color` - the colour of the background when selected.
* `textStyle: TextStyle?` - the style of the number (i.e. bold, font, etc...) when it is selected.

The below samples are a showcase of using either a single colour or multiple colours as backgrounds for the selected dates.

`convertSelectedDatesToCalendarDates()` can be found in any of the samples.

```
CalendarJetpackCompose(
    year = year,
    month = month,
    selectedDates = convertSelectedDatesToCalendarDates(dates = selectedDates),
    onDayPressed = viewModel::updateSelectedDate, // function to trigger when a day is pressed. It takes in a long (date in milliseconds from epoch)
    onNavigateMonthPressed = viewModel::updateSelectedMonth,
    canNavigateMonths = true,
)
```

**Select Date with One Colour**

<img src="https://user-images.githubusercontent.com/12566260/111799368-30c3cc00-8888-11eb-8d7e-89716d818312.gif" width="205"/>

[link to sample]


**Select Date with Multiple Colours + Padding**

<img src="https://user-images.githubusercontent.com/12566260/111799485-4a651380-8888-11eb-94fc-352b45ca085b.gif" width="205"/>

[link to sample]

