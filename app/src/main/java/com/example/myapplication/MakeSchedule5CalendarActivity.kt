package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import com.example.myapplication.compose.MakeSchedule5Calendar
import com.example.myapplication.databinding.ActivityMakeSchedule5CalendarBinding

class MakeSchedule5CalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakeSchedule5CalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
        supportActionBar?.hide()
        binding = ActivityMakeSchedule5CalendarBinding.inflate(layoutInflater)
        binding.apply {
            composeView.setContent {
                MaterialTheme {
                    MakeSchedule5Calendar(onSelectedDate = {})
                }
            }
        }
        setContentView(binding.root)
        setupButton()
    }

    private fun setupButton() {
        binding.submitButton.isEnabled = true
        binding.submitButton.setBackgroundResource(R.drawable.button_enabled)
        binding.submitButton.setOnClickListener {
            val intent = Intent(this, MakeSchedule3Activity::class.java)
            startActivity(intent)
        }
    }
}
