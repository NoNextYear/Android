package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import com.example.myapplication.compose.MakeSchedule5Calendar
import com.example.myapplication.databinding.ActivityMakeSchedule5CalendarBinding
import java.time.LocalDate

class MakeSchedule5CalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakeSchedule5CalendarBinding
    private val selectedDates = mutableListOf<LocalDate>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
        supportActionBar?.hide()
        binding = ActivityMakeSchedule5CalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            composeView.setContent {
                MaterialTheme {
                    MakeSchedule5Calendar(onSelectedDate = { startDate, endDate ->
                        selectedDates.clear()
                        var currentDate = startDate
                        if (endDate != null) {
                            while (!currentDate.isAfter(endDate)) {
                                selectedDates.add(currentDate)
                                currentDate = currentDate.plusDays(1)
                            }
                        } else {
                            selectedDates.add(startDate)
                        }
                    })
                }
            }
        }

        setupButton()
    }

    private fun setupButton() {
        binding.submitButton.isEnabled = true
        binding.submitButton.setBackgroundResource(R.drawable.button_enabled)
        binding.submitButton.setOnClickListener {
            if (selectedDates.isNotEmpty()) {
                val intent = Intent(this, MakeSchedule5Activity::class.java).apply {
                    selectedDates.forEachIndexed { index, date ->
                        putExtra("date_$index", date.toString())
                    }
                    putExtra("date_count", selectedDates.size)
                    putExtra("current_index", 0) // 시작 인덱스 초기화
                }
                startActivity(intent)
            }
        }
    }
}
