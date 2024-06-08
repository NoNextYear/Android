package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class MakeSchedule4CompleteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_schedule4_complete)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
        supportActionBar?.hide()
        // 여기서 필요한 초기화 작업을 수행합니다.
        val backButton: AppCompatButton = findViewById(R.id.go_back_button)
        val teamName = intent.getStringExtra("teamName")
        Log.e("test1234",teamName.toString())
        backButton.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java).apply {
                putExtra("teamName", "HCI 프로젝트")
            }
            startActivity(intent)
        }
    }
}
