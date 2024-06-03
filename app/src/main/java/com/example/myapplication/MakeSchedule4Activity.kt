package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class MakeSchedule4Activity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_make_schedule4)
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                    )
            supportActionBar?.hide()

            val teamNameTextView: TextView = findViewById(R.id.team_name_layout)
            val startTimeTextView: TextView = findViewById(R.id.select_start_time_layout)
            val endTimeTextView: TextView = findViewById(R.id.select_end_time_layout)
            val backButton: ImageButton = findViewById(R.id.back_btn)
            val submitButton: AppCompatButton = findViewById(R.id.submit_button)

            val teamName = intent.getStringExtra("teamName")
            val startTime = intent.getStringExtra("startTime")
            val endTime = intent.getStringExtra("endTime")

            teamNameTextView.text = teamName
            startTimeTextView.text = startTime
            endTimeTextView.text = endTime

            backButton.setOnClickListener {
                val intent = Intent(this, MakeSchedule3Activity::class.java).apply {
                    putExtra("teamName", teamName)
                    putExtra("startTime", startTime)
                    putExtra("endTime", endTime)
                }
                startActivity(intent)
            }
            submitButton.setOnClickListener {
                val intent = Intent(this, MakeSchedule4CompleteActivity::class.java)
                startActivity(intent)
            }
        }
}
