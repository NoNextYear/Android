package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.fragments.CalendarFragment
import com.example.myapplication.fragments.ChatFragment
import com.example.myapplication.fragments.FriendFragment
import com.example.myapplication.fragments.MainFragment
import com.example.myapplication.fragments.ScheduleFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startFragment()
    }

    private fun startFragment() {

        val mainBnv = findViewById<BottomNavigationView>(R.id.main_bnv)

        mainBnv.run()
        {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.navigation_calendar -> {
                        // 다른 프래그먼트 화면으로 이동하는 기능
                        val calendarFragment = CalendarFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fl_container, calendarFragment).commit()
                    }
                    R.id.navigation_schedule -> {
                        val scheduleFragment = ScheduleFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fl_container, scheduleFragment).commit()
                    }
                    R.id.navigation_home -> {
                        val mainFragment = MainFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fl_container, mainFragment).commit()
                    }
                    R.id.navigation_chat -> {
                        val chatFragment = ChatFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fl_container, chatFragment).commit()
                    }
                    R.id.navigation_friend -> {
                        val friendFragment = FriendFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fl_container, friendFragment).commit()
                    }

                }
                true
            }
            selectedItemId = R.id.navigation_home
        }
    }

}