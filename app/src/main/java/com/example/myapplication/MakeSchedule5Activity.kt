package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.NumberPicker
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.ItemScheduleAdapter

class MakeSchedule5Activity : AppCompatActivity() {

    private lateinit var numberPicker: NumberPicker
    private lateinit var recyclerView: RecyclerView
    private lateinit var submitButton: Button
    private lateinit var timeRangeAdapter: ItemScheduleAdapter

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

        numberPicker.minValue = 1
        numberPicker.maxValue = 5
        numberPicker.value = 1

        timeRangeAdapter = ItemScheduleAdapter(numberPicker.value)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = timeRangeAdapter

        numberPicker.setOnValueChangedListener { _, _, newVal ->
            timeRangeAdapter.updateRangeCount(newVal)
        }

        submitButton.setOnClickListener {
            val selectedTimes = timeRangeAdapter.getSelectedTimes()
            val intent = Intent(this, MakeSchedule6Activity::class.java)
            selectedTimes.forEachIndexed { index, pair ->
                intent.putExtra("date_$index", pair.first)
                intent.putStringArrayListExtra("timeRanges_$index", ArrayList(pair.second))
            }
            startActivity(intent)
        }
    }
}
