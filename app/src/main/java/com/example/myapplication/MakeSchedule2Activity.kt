package com.example.myapplication

import MakeSchedule2
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import com.example.myapplication.databinding.ActivityMakeschedule2Binding

class MakeSchedule2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityMakeschedule2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
        supportActionBar?.hide()
        binding = ActivityMakeschedule2Binding.inflate(layoutInflater)
        binding.apply {
            composeView.setContent {
                MaterialTheme {
                    MakeSchedule2(onSelectedDate = {})
                }
            }
        }

        setContentView(binding.root)
        setupButton()

    }
    private fun setupButton() {
        binding.btnNextMakeSchedule2.isEnabled = true
        binding.btnNextMakeSchedule2.setBackgroundResource(R.drawable.button_enabled)
        binding.btnNextMakeSchedule2.setOnClickListener {
            val intent = Intent(this, MakeSchedule3Activity::class.java)
            startActivity(intent)
        }
    }
}