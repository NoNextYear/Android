package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import java.text.SimpleDateFormat
import java.util.*

class MakeSchedule4Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_schedule4)

        val startTimeTextView: TextView = findViewById(R.id.select_start_time_layout)
        val endTimeTextView: TextView = findViewById(R.id.select_end_time_layout)
        val backButton: ImageButton = findViewById(R.id.back_btn)
        val submitButton : AppCompatButton = findViewById(R.id.submit_button)

        val startTime = intent.getStringExtra("startTime")
        val endTime = intent.getStringExtra("endTime")

        if (startTime != null) {
            startTimeTextView.text = formatTime(startTime)
        }
        if (endTime != null) {
            endTimeTextView.text = formatTime(endTime)
        }

        backButton.setOnClickListener {
            val intent = Intent(this, MakeSchedule3Activity::class.java)
            startActivity(intent)
        }
        submitButton.setOnClickListener{
            val intent = Intent(this, MakeSchedule4CompleteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun formatTime(time: String): String {
        return try {
            val inputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val date: Date? = inputFormat.parse(time)
            if (date != null) {
                val calendar = Calendar.getInstance()
                calendar.time = date
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val minute = calendar.get(Calendar.MINUTE)
                val period = if (hour < 12) "오전" else "오후"
                val formattedHour = if (hour % 12 == 0) 12 else hour % 12
                String.format("%s %02d시 %02d분", period, formattedHour, minute)
            } else {
                time
            }
        } catch (e: Exception) {
            time
        }
    }
}
