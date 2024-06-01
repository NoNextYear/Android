package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemScheduleTimeBinding

class ItemScheduleAdapter(private var rangeCount: Int) :
    RecyclerView.Adapter<ItemScheduleAdapter.TimeRangeViewHolder>() {

    class TimeRangeViewHolder(val binding: ItemScheduleTimeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeRangeViewHolder {
        val binding = ItemScheduleTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeRangeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeRangeViewHolder, position: Int) {
        // TimeRangeViewHolder 바인딩 처리
    }

    override fun getItemCount(): Int = rangeCount

    fun updateRangeCount(newCount: Int) {
        rangeCount = newCount
        notifyDataSetChanged()
    }
}
