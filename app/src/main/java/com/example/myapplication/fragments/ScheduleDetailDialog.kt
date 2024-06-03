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

        // Set the text based on the passed data
        detailDate.text = arguments?.getString("date") ?: "N/A"
        detailTime.text = arguments?.getString("time") ?: "N/A"
        optimalScore.text = arguments?.getString("score") ?: "N/A"

        // 참가자 정보 설정
        val selectedOption = arguments?.getInt("selected_option") ?: 1
        when (selectedOption) {
            1 -> {
                view.findViewById<TextView>(R.id.participant1).visibility = View.VISIBLE
                view.findViewById<TextView>(R.id.participant2).visibility = View.VISIBLE
                view.findViewById<TextView>(R.id.participant3).visibility = View.VISIBLE
                view.findViewById<TextView>(R.id.participant4).visibility = View.VISIBLE
                view.findViewById<TextView>(R.id.participant5).visibility = View.GONE
                view.findViewById<TextView>(R.id.participant6).visibility = View.GONE
                view.findViewById<TextView>(R.id.participant7).visibility = View.GONE
                view.findViewById<TextView>(R.id.participant8).visibility = View.GONE
                view.findViewById<TextView>(R.id.participant9).visibility = View.GONE
            }
            2 -> {
                view.findViewById<TextView>(R.id.participant1).visibility = View.GONE
                view.findViewById<TextView>(R.id.participant2).visibility = View.GONE
                view.findViewById<TextView>(R.id.participant3).visibility = View.GONE
                view.findViewById<TextView>(R.id.participant4).visibility = View.GONE
                view.findViewById<TextView>(R.id.participant5).visibility = View.VISIBLE
                view.findViewById<TextView>(R.id.participant6).visibility = View.VISIBLE
                view.findViewById<TextView>(R.id.participant7).visibility = View.VISIBLE
                view.findViewById<TextView>(R.id.participant8).visibility = View.GONE
                view.findViewById<TextView>(R.id.participant9).visibility = View.GONE
            }
            3 -> {
                view.findViewById<TextView>(R.id.participant1).visibility = View.GONE
                view.findViewById<TextView>(R.id.participant2).visibility = View.GONE
                view.findViewById<TextView>(R.id.participant3).visibility = View.GONE
                view.findViewById<TextView>(R.id.participant4).visibility = View.GONE
                view.findViewById<TextView>(R.id.participant5).visibility = View.GONE
                view.findViewById<TextView>(R.id.participant6).visibility = View.GONE
                view.findViewById<TextView>(R.id.participant7).visibility = View.GONE
                view.findViewById<TextView>(R.id.participant8).visibility = View.VISIBLE
                view.findViewById<TextView>(R.id.participant9).visibility = View.VISIBLE
            }
        }

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
        fun newInstance(date: String, time: String, score: String, selectedOption: Int): ScheduleDetailDialog {
            val args = Bundle()
            args.putString("date", date)
            args.putString("time", time)
            args.putString("score", score)
            args.putInt("selected_option", selectedOption)
            val fragment = ScheduleDetailDialog()
            fragment.arguments = args
            return fragment
        }
    }
}
