
package com.example.myapplication

import android.os.Bundle
import android.widget.NumberPicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import java.util.Locale

class MakeSchedule3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_schedule_3)

        // 로케일을 한국어로 설정
        val locale = Locale("ko", "KR")
        Locale.setDefault(locale)
        resources.configuration.setLocale(locale)
        resources.updateConfiguration(resources.configuration, resources.displayMetrics)

        val startTimePicker: TimePicker = findViewById(R.id.start_time_picker)
        val endTimePicker: TimePicker = findViewById(R.id.end_time_picker)
        val submitButton: AppCompatButton = findViewById(R.id.submit_button)

        startTimePicker.setIs24HourView(false)
        endTimePicker.setIs24HourView(false)

        // TimePicker의 AM/PM 텍스트를 한국어로 변경
        setAmPmText(startTimePicker)
        setAmPmText(endTimePicker)

        submitButton.setOnClickListener {
            val startHour = if (android.os.Build.VERSION.SDK_INT >= 23) startTimePicker.hour else startTimePicker.currentHour
            val startMinute = if (android.os.Build.VERSION.SDK_INT >= 23) startTimePicker.minute else startTimePicker.currentMinute

            val endHour = if (android.os.Build.VERSION.SDK_INT >= 23) endTimePicker.hour else endTimePicker.currentHour
            val endMinute = if (android.os.Build.VERSION.SDK_INT >= 23) endTimePicker.minute else endTimePicker.currentMinute

            if (startHour > endHour || (startHour == endHour && startMinute >= endMinute)) {
                Toast.makeText(this, "시간을 재설정해주세요!", Toast.LENGTH_SHORT).show()
            } else {
                //다음화면 이동?
            }
        }

    }
    private fun setAmPmText(timePicker: TimePicker) {
        try {
            val amPmStrings = arrayOf("오전", "오후")
            val classForid = Class.forName("com.android.internal.R\$id")
            val fieldAmPm = classForid.getField("amPm")
            val amPmView = timePicker.findViewById<NumberPicker>(fieldAmPm.getInt(null))
            amPmView.minValue = 0
            amPmView.maxValue = 1
            amPmView.displayedValues = amPmStrings
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}