package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.adapter.ScheduleOptionAdapter
import com.example.myapplication.databinding.FragmentMakeSchedule6Binding

class MakeSchedule6Fragment : Fragment() {

    private var _binding: FragmentMakeSchedule6Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMakeSchedule6Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 전체 화면 모드 활성화
        requireActivity().window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )

        // 액션바 숨기기
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        // 예시 데이터 생성
        val options = listOf("1안 추천!", "2안 추천!", "3안 추천!")

        // 어댑터 초기화 및 ListView 설정
        val adapter = ScheduleOptionAdapter(requireContext(), options)
        binding.listScheduleOptions.adapter = adapter

        // 포기하기 버튼 클릭 리스너 설정
        binding.btnCancel.setOnClickListener {
            val dialog = DeleteConfirmationDialog()
            dialog.show(parentFragmentManager, "DeleteConfirmationDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        // 액션바 다시 표시
        (requireActivity() as AppCompatActivity).supportActionBar?.show()

        // 시스템 UI 복원
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }
}
