package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.FriendAdapter
import com.example.myapplication.databinding.FragmentFriendBinding
import com.example.myapplication.model.Friend

class FriendFragment : Fragment() {

    private var _binding: FragmentFriendBinding? = null
    private val binding get() = _binding!!

    private lateinit var friendAdapter: FriendAdapter
    private lateinit var friendList: List<Friend>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFriendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        friendList = listOf(
            Friend("가나다", R.drawable.ic_circle),
            Friend("가다나", R.drawable.ic_circle),
            Friend("다나카", R.drawable.ic_circle),
            Friend("다카나", R.drawable.ic_circle),
            Friend("다나키", R.drawable.ic_circle),
            Friend("나성범", R.drawable.ic_circle),
            Friend("박찬호", R.drawable.ic_circle),
            Friend("박지성", R.drawable.ic_circle),
            Friend("이청용", R.drawable.ic_circle),
            Friend("김덕배", R.drawable.ic_circle),
            Friend("손흥민", R.drawable.ic_circle),
            Friend("이강인", R.drawable.ic_circle)
        )

        friendAdapter = FriendAdapter(friendList) { isAnyFriendSelected ->
            binding.createGroupButton.visibility = if (isAnyFriendSelected) View.VISIBLE else View.GONE
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = friendAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
