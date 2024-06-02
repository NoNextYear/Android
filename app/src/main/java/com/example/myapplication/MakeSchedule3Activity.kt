package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class MakeSchedule3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_schedule3)

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
        supportActionBar?.hide()

        val startTimePicker: TimePicker = findViewById(R.id.start_time_picker)
        val endTimePicker: TimePicker = findViewById(R.id.end_time_picker)
        val submitButton: AppCompatButton = findViewById(R.id.submit_button)

        startTimePicker.setIs24HourView(false)
        endTimePicker.setIs24HourView(false)

        submitButton.setOnClickListener {
            Log.d("MakeSchedule3Activity", "Submit button clicked")

            val startHour = if (android.os.Build.VERSION.SDK_INT >= 23) startTimePicker.hour else startTimePicker.currentHour
            val startMinute = if (android.os.Build.VERSION.SDK_INT >= 23) startTimePicker.minute else startTimePicker.currentMinute

            val endHour = if (android.os.Build.VERSION.SDK_INT >= 23) endTimePicker.hour else endTimePicker.currentHour
            val endMinute = if (android.os.Build.VERSION.SDK_INT >= 23) endTimePicker.minute else endTimePicker.currentMinute

            Log.d("MakeSchedule3Activity", "startHour: $startHour, startMinute: $startMinute, endHour: $endHour, endMinute: $endMinute")

            if (startHour > endHour || (startHour == endHour && startMinute >= endMinute)) {
                Log.d("MakeSchedule3Activity", "Invalid time range")
                Toast.makeText(this, "시간을 재설정해주세요!", Toast.LENGTH_SHORT).show()
            } else {
                val startTime = formatTime(startHour, startMinute)
                val endTime = formatTime(endHour, endMinute)

                val intent = Intent(this, MakeSchedule4Activity::class.java)
                intent.putExtra("startTime", startTime)
                intent.putExtra("endTime", endTime)
                startActivity(intent)
                Log.d("MakeSchedule3Activity", "Starting MakeSchedule4Activity")
            }
        }
    }

    private fun formatTime(hour: Int, minute: Int): String {
        val period = if (hour < 12) "오전" else "오후"
        val adjustedHour = if (hour % 12 == 0) 12 else hour % 12
        return String.format("%s %02d시 %02d분", period, adjustedHour, minute)
    }
}
