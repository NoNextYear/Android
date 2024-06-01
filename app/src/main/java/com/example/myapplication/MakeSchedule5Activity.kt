package com.example.myapplication

import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import android.widget.NumberPicker
import android.view.View
import android.widget.TextView

class MakeSchedule5Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_schedule5)
        window.decorView.apply {
            systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }
        val circularTimePicker = findViewById<CircularTimePickerView>(R.id.circularTimePicker)
        val numberPicker = findViewById<NumberPicker>(R.id.numberPicker)
        val numberPickerTextView = numberPicker.getChildAt(1) as? TextView
        numberPickerTextView?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        numberPicker.minValue = 1
        numberPicker.maxValue = 10
        numberPicker.setOnValueChangedListener { _, _, newVal ->
            circularTimePicker.setRangeCount(newVal)
        }
    }
}
