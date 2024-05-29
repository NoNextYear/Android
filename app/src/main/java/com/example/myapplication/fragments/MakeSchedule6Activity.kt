package com.example.myapplication.fragments


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.ScheduleOptionAdapter
import com.example.myapplication.databinding.ActivityMakeSchedule6Binding

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

        // 예시 데이터 생성
        val options = listOf("1안 추천!", "2안 추천!", "3안 추천!")

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
            val intent = Intent(this, ConfirmationActivity::class.java).apply {
                putExtra("selectedOption", selectedOption)
            }
            startActivity(intent)
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