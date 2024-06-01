package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideSystemBars()
        setupWeekCalendar()
    }

    private fun setupWeekCalendar() {
        val calendar = Calendar.getInstance()
        val today = calendar.time

        // Start calendar from the beginning of the week (Sunday)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        val dateFormat = SimpleDateFormat("d", Locale.getDefault())
        val todayFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val todayStr = todayFormat.format(today)

        val daysOfWeek = arrayOf(
            binding.sundayDate to binding.sundayLabel,
            binding.mondayDate to binding.mondayLabel,
            binding.tuesdayDate to binding.tuesdayLabel,
            binding.wednesdayDate to binding.wednesdayLabel,
            binding.thursdayDate to binding.thursdayLabel,
            binding.fridayDate to binding.fridayLabel,
            binding.saturdayDate to binding.saturdayLabel
        )

        for ((dateView, labelView) in daysOfWeek) {
            val dateStr = dateFormat.format(calendar.time)
            dateView.text = dateStr

            // Set a fixed size for the dateView
            dateView.width = resources.getDimensionPixelSize(R.dimen.date_view_size)
            dateView.height = resources.getDimensionPixelSize(R.dimen.date_view_size)

            // Check if this date is today
            if (todayStr == todayFormat.format(calendar.time)) {
                // Apply a blue circle background and white text color
                dateView.setBackgroundResource(R.drawable.circle_background)
                dateView.setTextColor(resources.getColor(android.R.color.white, null))
            } else {
                // Clear background for non-today dates
                dateView.background = null
                dateView.setTextColor(resources.getColor(android.R.color.black, null))
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
    }

    private fun hideSystemBars() {
        activity?.window?.decorView?.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}