package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.fragments.ScheduleDetailDialog
import com.google.android.material.card.MaterialCardView

class ScheduleOptionAdapter(private val context: Context, private val options: List<String>) :
    RecyclerView.Adapter<ScheduleOptionAdapter.ViewHolder>() {

    private var selectedPosition: Int = -1

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: MaterialCardView = view.findViewById(R.id.card_view)
        val scheduleRank: TextView = view.findViewById(R.id.schedule_rank)
        val scheduleDate: TextView = view.findViewById(R.id.schedule_date)
        val scheduleTime: TextView = view.findViewById(R.id.schedule_time)
        val btnDetail: Button = view.findViewById(R.id.btn_detail)

        init {
            view.setOnClickListener {
                selectedPosition = adapterPosition
                notifyDataSetChanged()
            }

            btnDetail.setOnClickListener {
                // 상세보기 버튼 클릭 시 동작
                val scheduleDate = "24년 5월 31일"
                val scheduleTime = "2:00PM ~ 4:00PM"
                val score = "99점" // 예시 점수

                val dialog = ScheduleDetailDialog.newInstance(scheduleDate, scheduleTime, score)
                dialog.show((context as FragmentActivity).supportFragmentManager, "ScheduleDetailDialog")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule_option, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val option = options[position]
        holder.scheduleRank.text = option
        holder.scheduleDate.text = "24년 5월 31일"
        holder.scheduleTime.text = "2:00PM ~ 4:00PM"

        if (position == selectedPosition) {
            holder.cardView.setCardBackgroundColor(context.resources.getColor(R.color.white))
            holder.cardView.strokeColor = context.resources.getColor(R.color.selected_item_color)
            holder.cardView.strokeWidth = 4 // 원하는 테두리 두께를 설정
        } else {
            holder.cardView.setCardBackgroundColor(context.resources.getColor(R.color.white))
            holder.cardView.strokeColor = context.resources.getColor(android.R.color.transparent)
            holder.cardView.strokeWidth = 0 // 기본 상태에서 테두리 제거
        }
    }

    override fun getItemCount(): Int {
        return options.size
    }

    fun getSelectedOption(): String? {
        return if (selectedPosition >= 0) options[selectedPosition] else null
    }
}
