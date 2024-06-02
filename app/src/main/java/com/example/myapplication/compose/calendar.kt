package com.example.myapplication.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Preview
@Composable
fun TestCalendar(){
    BasicCalendar(onSelectedDate = {})
}
data class BasicCalendarConfig (
    val yearRange: IntRange = IntRange(1970, 2100),
    val locale: Locale = Locale.KOREAN
)
val scheduleData1 = ScheduleData("헬스 트레이딩 7주차",
    LocalDate.of(2024,6,17),"12:00","낮 12시00분 ~ 오후2시00분",
    Color.Red)
val scheduleData2 = ScheduleData("현장 답사",
    LocalDate.of(2024,6,19),"12:00","낮 12시00분 ~ 오후2시00분",
    Color.Blue)
val scheduleData3 = ScheduleData("편의점 아르바이트",
    LocalDate.of(2024,6,15),"17:00","오후 17시00분 ~ 오후23시00분",
    Color.Cyan)
val scheduleData4 = ScheduleData("HCI 발표",
    LocalDate.of(2024,6,11),"12:00","낮 12시00분 ~ 오후1시30분",
    Color.Magenta)
val scheduleList = arrayListOf(scheduleData1, scheduleData2, scheduleData3, scheduleData4)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BasicCalendar(
    modifier: Modifier = Modifier,
    currentDate: LocalDate = LocalDate.now(),
    config: BasicCalendarConfig = BasicCalendarConfig(),
    onSelectedDate: (LocalDate) -> Unit
) {
    val initialPage = (currentDate.year - config.yearRange.first) * 12 + currentDate.monthValue - 1
    var currentSelectedDate by remember { mutableStateOf(currentDate) }
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
        val headerText = currentMonth.format(DateTimeFormatter.ofPattern("MMM",Locale.ENGLISH))
        CalendarHeader(
            modifier = Modifier.padding(42.dp),
            text = headerText
        )
        HorizontalPager(
            state = pagerState
        ) { page ->
            val date = LocalDate.of(
                config.yearRange.first + page / 12,
                page % 12 + 1,
                1
            )
            if (page in pagerState.currentPage - 1..pagerState.currentPage + 1) { // 페이징 성능 개선을 위한 조건문
                CalendarMonthItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 42.dp),
                    currentDate = date,
                    selectedDate = currentSelectedDate,
                    onSelectedDate = { selectedDate ->
                        currentSelectedDate = selectedDate
                    }
                )
            }
        }
        SelectedDayEvent(modifier = Modifier.fillMaxWidth(),
            selectedDate = currentSelectedDate,
            onSelectedDate = { selectedDate ->
                currentSelectedDate = selectedDate})
    }
}



@Composable
fun CalendarHeader(
    modifier: Modifier = Modifier,
    text: String,
) {
    Box(modifier = modifier) {
        Text(
            text = text,
            //style = MaterialTheme.typography.bodyMedium
            fontSize = 25.sp
        )
    }
}
@Composable
fun SelectedDayEvent(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
    onSelectedDate: (LocalDate) -> Unit) {
    var checkSchedule : Boolean = false
    var scheduleTodo =ScheduleData("헬스 트레이딩 7주차",selectedDate,"12:00","낮 12시00분 ~ 오후2시00분",Color.Red)
    for(schedule in scheduleList){
        if(selectedDate == schedule.date) {
            checkSchedule = true
            scheduleTodo = schedule
        }
    }
    Column(modifier=modifier.padding(20.dp)) {
        Row {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                    Text(
                        text = selectedDate.format(
                            DateTimeFormatter.ofPattern(
                                "dd",
                                Locale.ENGLISH
                            )
                        ),
                        fontSize = 43.sp
                    )
                    Text(text = selectedDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN), fontSize = 18.sp)

            }
            Spacer(modifier = Modifier.width(30.dp))
            if(selectedDate == LocalDate.now()){
                Column(modifier= Modifier.padding(top = 25.dp)) {
                    Text(
                        text = "오늘",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if(checkSchedule){
                        Text(
                            text = "현재 일정이 1개 있어요",
                            fontSize = 16.sp,
                            color = Color.Gray)
                    }
                    else {
                        Text(
                            text = "일정이 없어요..",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }

                }
            }
            else{
                Column(modifier= Modifier.padding(top = 28.dp)) {
                    Text(
                        text = "",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if(checkSchedule){
                        Text(
                            text = "현재 일정이 1개 있어요",
                            fontSize = 16.sp,
                            color = Color.Gray)
                    }
                    else {
                        Text(
                            text = "일정이 없어요..",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }

                }
            }

        }
        if(!checkSchedule){
            Image(
                painter = painterResource(id = R.drawable.sad),
                contentDescription = null,
                modifier = modifier
                    .fillMaxSize()
                    .padding(100.dp)
            )
        }
        else{
            //스케줄을 n개 출력하도록 수정해야됨

            SchedileInCalendar(scheduleTodo)
        }
    }
}

@Composable
fun SchedileInCalendar(scheduleData: ScheduleData) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.padding(top = 10.dp)){
            Text(text = scheduleData.time, fontSize = 30.sp)
            Spacer(modifier = Modifier.width(15.dp))
            Image(painter = painterResource(id = R.drawable.line8),
                contentDescription = null,
                modifier = Modifier
                    .width(280.dp)
                    .height(40.dp))
        }
        Row(modifier = Modifier.padding(start = 90.dp)) {
            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(14.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(
                        scheduleData.color
                    )
            )
            Column(modifier = Modifier.padding(start = 14.dp)) {
                Text(
                    text = scheduleData.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,

                )
                Text(
                    text = scheduleData.duringtime,
                    fontSize = 14.sp,
                    color = Color.Gray
                )

            }
        }

    }
}
@Composable
fun CalendarMonthItem(
    modifier: Modifier = Modifier,
    currentDate: LocalDate,
    selectedDate: LocalDate,
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
            for (i in 1 until firstDayOfWeek) { // 처음 날짜가 시작하는 요일 전까지 빈 박스 생성
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
                val isSelected = remember(selectedDate) {
                    selectedDate.compareTo(date) == 0
                }
                CalendarDay(
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
fun CalendarDay(
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
                if (isToday) Color.Gray
                else if (isSelected) Color(0xFF18A0FB)
                else Color.Transparent
            )
            .clickable { onSelectedDate(date) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        val textColor =
            if(isSelected) Color.LightGray
            else Color.Black
        Text(
            modifier = Modifier,
            textAlign = TextAlign.Center,
            text = date.dayOfMonth.toString(),
            //style = MaterialTheme.typography.bodyMedium,
            fontSize = 18.sp,
            color = textColor
        )
        for (schedule in scheduleList){
            if(date == schedule.date) {
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(shape = RoundedCornerShape(4.dp))
                        .background(
                            schedule.color
                        )
                )
            }
        }
    }
}

@Composable
fun DayOfWeek(
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        DayOfWeek.values().forEach { dayOfWeek ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
                //style = MaterialTheme.typography.bodyMedium,
                fontSize = 19.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}