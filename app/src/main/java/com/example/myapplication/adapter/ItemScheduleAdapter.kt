package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemScheduleTimeBinding

class ItemScheduleAdapter(private var rangeCount: Int) :
    RecyclerView.Adapter<ItemScheduleAdapter.TimeRangeViewHolder>() {

    private val selectedTimes: MutableList<Pair<String, List<String>>> = mutableListOf()

    class TimeRangeViewHolder(val binding: ItemScheduleTimeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeRangeViewHolder {
        val binding = ItemScheduleTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeRangeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeRangeViewHolder, position: Int) {
        // TimeRangeViewHolder 바인딩 처리
        // 예: holder.binding.textView.text = "시간 범위 ${position + 1}"
    }

    override fun getItemCount(): Int = rangeCount

    fun updateRangeCount(newCount: Int) {
        rangeCount = newCount
        notifyDataSetChanged()
    }

    fun getSelectedTimes(): List<Pair<String, List<String>>> {
        // 예시 데이터 넘김
        return listOf(
            Pair("2024년6월1일", listOf("10:00AM-11:00AM")),
            Pair("2024년6월2일", listOf("1:00PM-2:00PM")),
            Pair("2024년6월3일", listOf("3:00PM-4:00PM"))
        )
    }
}
