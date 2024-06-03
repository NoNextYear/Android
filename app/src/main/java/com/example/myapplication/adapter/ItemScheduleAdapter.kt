package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemScheduleTimeBinding
import java.time.LocalDate
class ItemScheduleAdapter(
    private val selectedDate: LocalDate // 하나의 날짜만 받도록 수정
) : RecyclerView.Adapter<ItemScheduleAdapter.TimeRangeViewHolder>() {

    private var rangeCount: Int = 1
    private val selectedTimes: MutableList<String> = MutableList(rangeCount) { "" } // 하나의 날짜에 대한 시간 범위만 관리

    class TimeRangeViewHolder(val binding: ItemScheduleTimeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeRangeViewHolder {
        val binding = ItemScheduleTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeRangeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeRangeViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = rangeCount

    fun updateRangeCount(newCount: Int) {
        rangeCount = newCount
        selectedTimes.clear()
        repeat(newCount) {
            selectedTimes.add("")
        }
        notifyDataSetChanged()
    }

    fun getSelectedTimes(): List<String> {
        return selectedTimes
    }
}
