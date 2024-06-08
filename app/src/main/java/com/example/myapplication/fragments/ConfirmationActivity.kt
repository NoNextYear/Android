package com.example.myapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.databinding.ActivityConfirmationBinding

class ConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 액션바 숨기기
        supportActionBar?.hide()

        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 상태바 및 네비게이션 바 숨기기
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )

        // 전달된 데이터를 가져와서 UI에 설정
        val selectedOption = intent.getStringExtra("selectedOption") ?: "N/A"
        val selectedDate = intent.getStringExtra("selectedDate") ?: "N/A"
        val selectedTime = intent.getStringExtra("selectedTime") ?: "N/A"

        binding.textDescription.text = selectedOption
        binding.textDate.text = selectedDate
        binding.textTime.text = selectedTime

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
