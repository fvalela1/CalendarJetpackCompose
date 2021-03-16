package com.fvalela.calendarjetpackcompose.month

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fvalela.calendarjetpackcompose.R
import java.time.Month
import java.time.format.TextStyle
import java.util.*

@Composable
internal fun MonthRow(
    year: Int,
    canNavigateMonths: Boolean,
    navigateMonthDrawableIds: Pair<Int, Int>,
    onNavigateMonthPressed: (Int, Int) -> Unit,
    month: Int,
    useMonthShortName: Boolean = false,
    verticalPadding: Dp,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = verticalPadding)
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (canNavigateMonths) {
            // ugly code to have a nice circle clickable area. Would need to look into how to do this in a nicer way.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(CircleShape)
                        .background(MaterialTheme.colors.background)
                        .clickable {
                            if (month == 1) {
                                onNavigateMonthPressed(12, year - 1)
                            } else {
                                onNavigateMonthPressed(month - 1, year)
                            }
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    painterResource(
                        id = navigateMonthDrawableIds.first,
                    ).let {
                        Image(
                            painter = it,
                            contentDescription = stringResource(R.string.navigate_month_previous_content_description),
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
                            modifier = Modifier.padding(5.dp),
                            alignment = Alignment.Center
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .weight(2f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = Month.of(month)
                    .getDisplayName(
                        if (useMonthShortName) TextStyle.SHORT_STANDALONE else TextStyle.FULL_STANDALONE,
                        Locale.getDefault(),
                    ),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(7.dp)
            )
        }
        if (canNavigateMonths) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(CircleShape)
                        .background(MaterialTheme.colors.background)
                        .clickable {
                            if (month == 12) {
                                onNavigateMonthPressed(1, year + 1)
                            } else {
                                onNavigateMonthPressed(month + 1, year)
                            }
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    painterResource(
                        id = navigateMonthDrawableIds.second,
                    ).let {
                        Image(
                            painter = it,
                            contentDescription = stringResource(R.string.navigate_month_next_content_description),
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
                            modifier = Modifier.padding(5.dp),
                            alignment = Alignment.Center
                        )
                    }
                }
            }
        }
    }
}