package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.Schedule
import com.example.myapplication.ScheduleAdapter
import com.example.myapplication.databinding.FragmentScheduleBinding

class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    private lateinit var meetingAdapter: ScheduleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupButton()
    }

    private fun setupRecyclerView() {
        meetingAdapter = ScheduleAdapter { selectedMeeting ->
            onMeetingSelected(selectedMeeting)
        }
        binding.meetingListRv.layoutManager = LinearLayoutManager(context)
        binding.meetingListRv.adapter = meetingAdapter

        // Load your data here and set it to the adapter
        val meetings = listOf(
            Schedule("배드민턴 동호회", 8),
            Schedule("HCI 프로젝트", 5)
        )
        meetingAdapter.submitList(meetings)
    }

    private fun setupButton() {
        binding.submitButton.isEnabled = false
        binding.submitButton.setBackgroundResource(R.drawable.button_disabled)
        binding.submitButton.setOnClickListener {
            Toast.makeText(context, "Button Clicked!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onMeetingSelected(meeting: Schedule) {
        binding.submitButton.isEnabled = true
        binding.submitButton.setBackgroundResource(R.drawable.button_enabled)
        meetingAdapter.setSelectedMeeting(meeting)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
