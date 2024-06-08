package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import com.example.myapplication.compose.ChatMessage
import com.example.myapplication.compose.ChatScreen
import com.example.myapplication.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val teamName = intent.getStringExtra("teamName")
        binding = ActivityChatBinding.inflate(layoutInflater)
        binding.apply {
            composeView.setContent {
                MaterialTheme {
                    if(teamName == null) {
                        val messages = listOf(
                            ChatMessage("송영민", "연대 산책했음\n그래서 우리 언제 볼까?", null, false),
                            ChatMessage("You", "ㄱㄷ 일정 만듬", null, true),
                            ChatMessage(
                                "You",
                                "윤짱님이 새로운 일정을 생성하셨어요.\n가능한 시간대를 설정해주세요~",
                                "시간대 설정하러 가기",
                                true,
                                true
                            ),
                            ChatMessage("줌창이", "ㅇㅈㅇㅈㅇㅈ", null, false),
                            ChatMessage(
                                "양동재",
                                "최적의 모임 일정이 계산되었어요\n지금 확인해보세요~",
                                "확인하러 가기",
                                false,
                                true
                            ),
                            ChatMessage("양동재", "오 마지막 표기자한테 일정 전달 버튼 생기네 옴춍나!!", null, false))
                            ChatScreen(messages)

                    }
                    else{
                        val messages = listOf( ChatMessage(
                    "You",
                    "윤짱님이 새로운 일정을 생성하셨어요.\n가능한 시간대를 설정해주세요~",
                    "시간대 설정하러 가기",
                    true,
                    true))
                        ChatScreen(messages,teamName)
                    }

                }
            }
        }

        setContentView(binding.root)


    }
}