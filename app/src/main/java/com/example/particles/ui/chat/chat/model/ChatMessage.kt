package com.example.particles.ui.chat.chat.model

import com.google.firebase.database.Exclude
import java.util.*


data class ChatMessage(
    val author: String? = null,
    val content: String? = null,
    val time: Long? = null
) {
    @get:Exclude
    val date: Calendar
        get() = Calendar.getInstance().apply {
            this@ChatMessage.time?.let {
                timeInMillis = it
            }
        }
}