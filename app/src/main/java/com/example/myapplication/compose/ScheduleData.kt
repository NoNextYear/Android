package com.example.myapplication.compose

import androidx.compose.ui.graphics.Color
import java.time.LocalDate

data class ScheduleData(val title:String, val date: LocalDate,val time:String,val duringtime:String, val color: Color)
