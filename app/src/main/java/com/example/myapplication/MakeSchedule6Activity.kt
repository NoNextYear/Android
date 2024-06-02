package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.ScheduleOptionAdapter
import com.example.myapplication.databinding.ActivityMakeSchedule6Binding
import com.example.myapplication.fragments.ConfirmationActivity
import com.example.myapplication.fragments.DeleteConfirmationDialog

class MakeSchedule6Activity : AppCompatActivity() {

    private lateinit var binding: ActivityMakeSchedule6Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeSchedule6Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // 전체 화면 모드 활성화
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )

        // 액션바 숨기기
        supportActionBar?.hide()

        // 인텐트에서 선택된 시간 범위 가져오기
        val options = mutableListOf<String>()
        for (i in 0..2) {
            val date = intent.getStringExtra("date_$i")
            val timeRanges = intent.getStringArrayListExtra("timeRanges_$i") ?: emptyList()
            val formattedTimeRanges = timeRanges.joinToString("\n")
            options.add("${i + 1}안 추천!\n$date\n$formattedTimeRanges")
        }

        // 어댑터 초기화 및 RecyclerView 설정
        val adapter = ScheduleOptionAdapter(this, options)
        binding.recyclerScheduleOptions.layoutManager = LinearLayoutManager(this)
        binding.recyclerScheduleOptions.adapter = adapter

        // 포기하기 버튼 클릭 리스너 설정
        binding.btnCancel.setOnClickListener {
            val dialog = DeleteConfirmationDialog()
            dialog.show(supportFragmentManager, "DeleteConfirmationDialog")
        }

        // 선택하기 버튼 클릭 리스너 설정
        binding.btnSelect.setOnClickListener {
            val selectedOption = adapter.getSelectedOption() ?: "선택한 일정 옵션 텍스트" // 실제 선택한 옵션 텍스트로 변경 필요
            val parts = selectedOption.split("\n")
            val description = parts[0]
            val date = parts[1]
            val time = parts.subList(2, parts.size).joinToString("\n")

            val intent = Intent(this, ConfirmationActivity::class.java).apply {
                putExtra("selectedOption", description)
                putExtra("selectedDate", date)
                putExtra("selectedTime", time)
            }
            startActivity(intent)
        }

        // 뒤로 가기 버튼 클릭 리스너 설정
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // 액션바 다시 표시
        supportActionBar?.show()

        // 시스템 UI 복원
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }
}
