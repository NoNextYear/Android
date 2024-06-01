import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.myapplication.compose.BasicCalendarConfig
import com.example.myapplication.compose.CalendarHeader
import com.example.myapplication.compose.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@Preview
@Composable
fun TestCalendar2() {
    MakeSchedule2(onSelectedDate = {})
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MakeSchedule2(
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
            text = "날짜를 선택해주세요",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 16.dp, top = 10.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        val headerText = currentMonth.format(DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH))
        CalendarHeader(
            modifier = Modifier.padding(top = 0.dp, bottom = 10.dp),
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
            if (page in pagerState.currentPage - 1..pagerState.currentPage + 1) {
                CalendarMonthItem2(
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
        DisplaySelectedDates(startSelectedDate, endSelectedDate)
    }
}

@Composable
fun DisplaySelectedDates(startSelectedDate: LocalDate?, endSelectedDate: LocalDate?) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            startSelectedDate == null -> {
                Text(
                    text = "선택한 날짜: 없음",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                )
            }
            endSelectedDate == null -> {
                Text(
                    text = "선택한 날짜: ${startSelectedDate.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일"))}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                )
            }
            else -> {
                Text(
                    text = "선택한 날짜: ${startSelectedDate.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일"))} ~ ${endSelectedDate.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일"))}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun CalendarMonthItem2(
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
                CalendarDay2(
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
fun CalendarDay2(
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
