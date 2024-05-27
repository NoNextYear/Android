package com.example.myapplication.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class ScheduleDetailDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_schedule_detail, container, false)

        val detailDate = view.findViewById<TextView>(R.id.detail_date)
        val detailTime = view.findViewById<TextView>(R.id.detail_time)
        val optimalScore = view.findViewById<TextView>(R.id.optimal_score)

        // 전달된 데이터를 기반으로 텍스트 설정 (필요시 Bundle을 통해 데이터 전달)
        detailDate.text = arguments?.getString("date") ?: "N/A"
        detailTime.text = arguments?.getString("time") ?: "N/A"
        optimalScore.text = arguments?.getString("score") ?: "N/A"

        view.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            dismiss()
        }

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireActivity(), R.style.CustomDialogTheme)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    companion object {
        fun newInstance(date: String, time: String, score: String): ScheduleDetailDialog {
            val args = Bundle()
            args.putString("date", date)
            args.putString("time", time)
            args.putString("score", score)
            val fragment = ScheduleDetailDialog()
            fragment.arguments = args
            return fragment
        }
    }
}
