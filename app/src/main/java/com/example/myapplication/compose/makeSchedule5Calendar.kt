package com.example.myapplication.compose

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.myapplication.R
import java.time.LocalDate
import java.time.YearMonth

@Preview
@Composable
fun TestCalendar5() {
    MakeSchedule5Calendar(onSelectedDate = {})
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MakeSchedule5Calendar(
    modifier: Modifier = Modifier,
    currentDate: LocalDate = LocalDate.now(),
    config: BasicCalendarConfig = BasicCalendarConfig(),
    onSelectedDate: (LocalDate) -> Unit
) {
    val initialPage = (currentDate.year - config.yearRange.first) * 12 + currentDate.monthValue - 1
    var startSelectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var endSelectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var currentPage by remember { mutableStateOf(initialPage) }
    val pageCount = (config.yearRange.last - config.yearRange.first) * 12
    val pagerState = rememberPagerState(initialPage = initialPage, pageCount = { pageCount })

    LaunchedEffect(pagerState.currentPage) {
        val addMonth = (pagerState.currentPage - currentPage).toLong()
        currentMonth = currentMonth.plusMonths(addMonth)
        currentPage = pagerState.currentPage
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 16.dp, top = 10.dp)
        )

        Text(
            text = "가능한 시간대를 입력할 날짜 선택",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 16.dp, top = 10.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.bm_jua)) // 폰트 적용 부분
        )
        HorizontalPager(
            state = pagerState
        ) { page ->
            val date = LocalDate.of(
                config.yearRange.first + page / 12,
                page % 12 + 1,
                1
            )
            if (page in pagerState.currentPage - 1..pagerState.currentPage + 1) {
                CalendarMonthItem5(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 42.dp),
                    currentDate = date,
                    startSelectedDate = startSelectedDate,
                    endSelectedDate = endSelectedDate,
                    onSelectedDate = { selectedDate ->
                        if (startSelectedDate == null || endSelectedDate != null) {
                            startSelectedDate = selectedDate
                            endSelectedDate = null
                        } else {
                            endSelectedDate = selectedDate
                            if (startSelectedDate!! > endSelectedDate) {
                                val temp = startSelectedDate
                                startSelectedDate = endSelectedDate
                                endSelectedDate = temp
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CalendarMonthItem5(
    modifier: Modifier = Modifier,
    currentDate: LocalDate,
    startSelectedDate: LocalDate?,
    endSelectedDate: LocalDate?,
    onSelectedDate: (LocalDate) -> Unit
) {
    val lastDay by remember { mutableStateOf(currentDate.lengthOfMonth()) }
    val firstDayOfWeek by remember { mutableStateOf(currentDate.dayOfWeek.value) }
    val days by remember { mutableStateOf(IntRange(1, lastDay).toList()) }
    Column(modifier = modifier) {
        DayOfWeek()
        LazyVerticalGrid(
            modifier = Modifier.height(200.dp),
            columns = GridCells.Fixed(7)
        ) {
            for (i in 1 until firstDayOfWeek) {
                item {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .padding(top = 10.dp)
                    )
                }
            }
            items(days) { day ->
                val date = currentDate.withDayOfMonth(day)
                val isSelected = remember(startSelectedDate, endSelectedDate) {
                    startSelectedDate != null && endSelectedDate != null &&
                            !date.isBefore(startSelectedDate) && !date.isAfter(endSelectedDate)
                } || (startSelectedDate != null && endSelectedDate == null && date == startSelectedDate)
                CalendarDay5(
                    modifier = Modifier.padding(top = 10.dp),
                    date = date,
                    isToday = date == LocalDate.now(),
                    isSelected = isSelected,
                    onSelectedDate = onSelectedDate,
                )
            }
        }
    }
}

@Composable
fun CalendarDay5(
    modifier: Modifier = Modifier,
    date: LocalDate,
    isToday: Boolean,
    isSelected: Boolean,
    onSelectedDate: (LocalDate) -> Unit
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .size(30.dp)
            .clip(shape = RoundedCornerShape(30.dp))
            .background(
                if (isToday) Color.Transparent
                else if (isSelected) Color(0xFF18A0FB)
                else Color.Transparent
            )
            .clickable { onSelectedDate(date) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val textColor =
            if (isSelected) Color.White
            else Color.Black
        Text(
            modifier = Modifier,
            textAlign = TextAlign.Center,
            text = date.dayOfMonth.toString(),
            fontSize = 18.sp,
            color = textColor
        )
    }
}
