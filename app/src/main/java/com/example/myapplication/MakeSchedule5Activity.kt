package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.NumberPicker
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.ItemScheduleAdapter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MakeSchedule5Activity : AppCompatActivity() {

    private lateinit var numberPicker: NumberPicker
    private lateinit var recyclerView: RecyclerView
    private lateinit var submitButton: Button
    private lateinit var timeRangeAdapter: ItemScheduleAdapter
    private lateinit var backBtn: ImageButton
    private lateinit var dateTextView: TextView
    private val selectedDates = mutableListOf<LocalDate>()
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_schedule5)
        // 전체 화면 모드 활성화
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )

        // 액션바 숨기기
        supportActionBar?.hide()
        numberPicker = findViewById(R.id.number_picker)
        recyclerView = findViewById(R.id.meeting_list_rv)
        submitButton = findViewById(R.id.submit_button)
        backBtn = findViewById(R.id.back_btn)
        dateTextView = findViewById(R.id.date_text)

        numberPicker.minValue = 1
        numberPicker.maxValue = 5
        numberPicker.value = 1

        val dateCount = intent.getIntExtra("date_count", 0)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        for (i in 0 until dateCount) {
            intent.getStringExtra("date_$i")?.let { dateString ->
                selectedDates.add(LocalDate.parse(dateString, formatter))
            }
        }

        currentIndex = intent.getIntExtra("current_index", 0)

        // 선택한 날짜를 기반으로 어댑터 초기화
        timeRangeAdapter = ItemScheduleAdapter(selectedDates[currentIndex])
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = timeRangeAdapter

        // 현재 선택한 날짜를 TextView에 표시
        dateTextView.text = selectedDates[currentIndex].format(DateTimeFormatter.ofPattern("yyyy년 M월 d일"))

        numberPicker.setOnValueChangedListener { _, _, newVal ->
            timeRangeAdapter.updateRangeCount(newVal)
        }

        backBtn.setOnClickListener {
            if (currentIndex > 0) {
                val intent = Intent(this, MakeSchedule5Activity::class.java).apply {
                    putExtras(this@MakeSchedule5Activity.intent.extras ?: Bundle())
                    putExtra("current_index", currentIndex - 1)
                }
                startActivity(intent)
            } else {
                val intent = Intent(this, MakeSchedule5CalendarActivity::class.java)
                startActivity(intent)
            }
        }

        submitButton.setOnClickListener {
            val selectedTimes = timeRangeAdapter.getSelectedTimes()
            val nextActivity = if (currentIndex + 1 < selectedDates.size) {
                MakeSchedule5Activity::class.java
            } else {
                MakeSchedule6Activity::class.java
            }
            val intent = Intent(this, nextActivity).apply {
                putExtras(this@MakeSchedule5Activity.intent.extras ?: Bundle())
                putExtra("current_index", currentIndex + 1)
                putExtra("date_${currentIndex}", selectedDates[currentIndex].toString())
                selectedTimes.forEachIndexed { index, pair ->
                    putExtra("timeRange_${currentIndex}_$index", pair.toString())
                }
            }
            startActivity(intent)
        }
    }
}
