package com.example.particles.ui.chat.model


data class Chat(
    val id: String? = null,
    val user1: String? = null,
    val user2: String? = null,
    var name: String? = null,
    var messages: ArrayList<ChatMessage> = arrayListOf()
)
