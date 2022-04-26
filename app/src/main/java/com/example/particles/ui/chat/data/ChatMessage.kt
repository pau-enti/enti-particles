package com.example.particles.ui.chat.data

import com.google.firebase.Timestamp

data class ChatMessage(
    val author: String,
    val content: String,
    val time: Timestamp
)