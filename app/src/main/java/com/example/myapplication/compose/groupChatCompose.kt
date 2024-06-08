package com.example.myapplication.compose

import android.content.Intent
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.MakeSchedule5CalendarActivity
import com.example.myapplication.R

data class ChatItem(val icon: ImageVector, val title: String, val count: Int)


@Composable
fun ChatListScreen(onItemClick: (String) -> Unit) {
    val chatItems = listOf(
        ChatItem(ImageVector.vectorResource(id = R.drawable.circle_with_fill), "배드민턴 동호회", 8),
        ChatItem(ImageVector.vectorResource(id = R.drawable.circle_with_fill), "HCI 프로젝트", 5),
        ChatItem(ImageVector.vectorResource(id = R.drawable.circle_with_fill), "수눙만점자와 세 똘마니", 4),
        ChatItem(ImageVector.vectorResource(id = R.drawable.circle_with_fill), "졸업 프로젝트", 4),
        ChatItem(ImageVector.vectorResource(id = R.drawable.circle_with_fill), "모프 기말 프로젝트", 8)
    )
    LazyColumn {
        items(chatItems) { item ->
            ChatListItem(item) {
                onItemClick(item.title)
            }
        }
    }
}

@Composable
fun ChatListItem(chatItem: ChatItem, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp).clickable {
                onItemClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = chatItem.icon,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(end = 16.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = chatItem.title,
                fontSize = 18.sp,
                color = Color.Black
            )
        }
        Text(
            text = chatItem.count.toString(),
            fontSize = 18.sp,
            color = Color.Gray
        )
    }
}
data class ChatMessage(
    val sender: String,
    val message: String,
    val message2 : String?,
    val isCurrentUser: Boolean,
    val isHighlighted: Boolean = false
)
@Composable
fun ChatDetailScreen() {
    Image(painter = painterResource(id = R.drawable.chatimg ), contentDescription = null,
        modifier = Modifier.fillMaxSize())
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(messages: List<ChatMessage>,teamname : String = "수능만점자와 세 똘마니 4") {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDCF8C6)) // Green background
    ) {
        TopAppBar(
            title = {
                Text(text = teamname, color = Color.Black)
            },
            navigationIcon = {
                IconButton(onClick = { backDispatcher?.onBackPressed() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            },
            actions = {
                IconButton(onClick = { /* Handle options */ }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            },
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            items(messages) { message ->
                ChatMessageItem(message)
            }
        }

        // Input field placeholder
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = "",
                onValueChange = { /* Handle text input */ },
                placeholder = { Text("Type a message") },
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White, shape = RoundedCornerShape(20.dp))
            )
            IconButton(onClick = { /* Handle send message */ }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                    tint = Color(0xFF128C7E) // Send button color
                )
            }
        }
    }
}
@Composable
fun ChatMessageItem(message: ChatMessage) {
    val backgroundColor = if (message.isCurrentUser) Color.Yellow else Color.White
    val alignment = if (message.isCurrentUser) Alignment.End else Alignment.Start
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .wrapContentWidth(alignment)
    ) {
        if (!message.isCurrentUser) {
            Text(
                text = message.sender,
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold,

            )
        }
        if(message.message2 == null){
            Box(
                modifier = Modifier
                    .background(backgroundColor, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Text(text = message.message, fontSize = 16.sp, color = Color.Black)
            }
        }
        else{
            Column(
                modifier = Modifier
                    .background(backgroundColor, shape = RoundedCornerShape(8.dp))
                    .padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = message.message, fontSize = 16.sp, color = Color.Black,modifier = Modifier.padding(bottom = 15.dp))
                Box(
                    modifier = Modifier
                        .background(Color(0xFF4ECB71), shape = RoundedCornerShape(8.dp))
                        .padding(10.dp).clickable{
                            val intent = Intent(context, MakeSchedule5CalendarActivity::class.java)
                            context.startActivity(intent)
                        }
                ) {
                    Text(text = message.message2, fontSize = 16.sp, color = Color.Black)
                }
            }
        }
    }
}
