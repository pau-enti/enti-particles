package com.example.particles.ui.chat.model

import com.google.firebase.database.Exclude


data class Chat(
    var id: Int? = null,
    val user1: String? = null,
    val user2: String? = null,
    var name: String? = null,
    var messages: ArrayList<ChatMessage> = arrayListOf()
) {
    constructor(
        user1: String,
        user2: String,
        name: String
    ) : this(idChatOf(user1, user2), user1, user2, name)

    companion object {
        fun idChatOf(user1: String, user2: String): Int = setOf(user1, user2).hashCode()
    }
}
