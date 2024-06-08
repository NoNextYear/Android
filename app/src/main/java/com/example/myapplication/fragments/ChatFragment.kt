package com.example.myapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.fragment.app.Fragment
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ChatActivity
import com.example.myapplication.compose.ChatListScreen
import com.example.myapplication.databinding.FragmentChatBinding


class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(layoutInflater)
        binding.apply {
            composeView.setContent {
                MaterialTheme{
                    Surface {
                        val navController = rememberNavController()
                        NavHost(navController = navController, startDestination = "chatList") {
                            composable("chatList") {
                                ChatListScreen { chatTitle ->
                                    val intent = Intent(activity, ChatActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                        }
                    }
                }
            }
        }
        return binding.root
    }
}

