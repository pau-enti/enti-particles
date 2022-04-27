package com.example.particles.ui.chat.data

import com.google.firebase.Timestamp

data class ChatMessage(
    val author: String? = null,
    val content: String? = null,
    val time: Timestamp? = null
)