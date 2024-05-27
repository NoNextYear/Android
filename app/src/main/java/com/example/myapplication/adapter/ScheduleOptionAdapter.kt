package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.fragments.ScheduleDetailDialog

class ScheduleOptionAdapter(private val context: Context, private val options: List<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return options.size
    }

    override fun getItem(position: Int): Any {
        return options[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_schedule_option, parent, false)

        val option = options[position]
        val scheduleDate = "24년 5월 31일" // 여기서 실제 날짜 데이터를 사용
        val scheduleTime = "2:00PM ~ 4:00PM" // 여기서 실제 시간 데이터를 사용
        val score = "99점" // 여기서 실제 점수 데이터를 사용

        view.findViewById<TextView>(R.id.schedule_rank).text = option
        view.findViewById<TextView>(R.id.schedule_date).text = scheduleDate
        view.findViewById<TextView>(R.id.schedule_time).text = scheduleTime

        view.findViewById<Button>(R.id.btn_detail).setOnClickListener {
            val dialog = ScheduleDetailDialog.newInstance(scheduleDate, scheduleTime, score)
            dialog.show((context as AppCompatActivity).supportFragmentManager, "ScheduleDetailDialog")
        }

        return view
    }
}
