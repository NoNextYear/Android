package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMakeschedule2UiBinding

class MakeSchedule2UiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakeschedule2UiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeschedule2UiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
        supportActionBar?.hide()

        setupButton()
        setupTextWatcher()
    }

    private fun setupButton() {
        binding.submitButton.isEnabled = false
        binding.submitButton.setBackgroundResource(R.drawable.button_disabled)
        binding.submitButton.setOnClickListener {
            val teamName = binding.teamNameEt.text.toString()
            val intent = Intent(this, MakeSchedule2Activity::class.java).apply {
                putExtra("teamName", teamName)
            }
            startActivity(intent)
        }
    }

    private fun setupTextWatcher() {
        binding.teamNameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Check if text is not empty
                if (!s.isNullOrEmpty()) {
                    binding.submitButton.isEnabled = true
                    binding.submitButton.setBackgroundResource(R.drawable.button_enabled)
                } else {
                    binding.submitButton.isEnabled = false
                    binding.submitButton.setBackgroundResource(R.drawable.button_disabled)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })
    }
}