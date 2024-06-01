package com.example.myapplication.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.Schedule


class ScheduleAdapter (private val onMeetingSelected: (Schedule) -> Unit) :
    RecyclerView.Adapter<ScheduleAdapter.MeetingViewHolder>() {

    private val meetings = mutableListOf<Schedule>()
    private var selectedMeeting: Schedule? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeetingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meeting_item, parent, false)
        return MeetingViewHolder(view)
    }

    override fun onBindViewHolder(holder: MeetingViewHolder, position: Int) {
        val meeting = meetings[position]
        holder.bind(meeting, meeting == selectedMeeting, onMeetingSelected)
    }

    override fun getItemCount(): Int = meetings.size

    fun submitList(meetings: List<Schedule>) {
        this.meetings.clear()
        this.meetings.addAll(meetings)
        notifyDataSetChanged()
    }

    fun setSelectedMeeting(meeting: Schedule) {
        selectedMeeting = meeting
        notifyDataSetChanged()
    }

    class MeetingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val meetingName: TextView = itemView.findViewById(R.id.meetingName)
        private val meetingCount: TextView = itemView.findViewById(R.id.meetingCount)

        fun bind(meeting: Schedule, isSelected: Boolean, onMeetingSelected: (Schedule) -> Unit) {
            meetingName.text = meeting.name
            meetingCount.text = meeting.count.toString()

            val selectedColor = ContextCompat.getColor(itemView.context,
                R.color.selected_item_color
            )
            itemView.setBackgroundColor(if (isSelected) selectedColor else Color.TRANSPARENT)

            itemView.setOnClickListener { onMeetingSelected(meeting) }
        }
    }

}
