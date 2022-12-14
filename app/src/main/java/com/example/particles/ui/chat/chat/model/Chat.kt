package com.example.particles.ui.chat.chat.model

import com.example.particles.ui.chat.User


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
        name: String? = null
    ) : this(idChatOf(user1, user2), user1, user2, name)

    companion object {
        fun idChatOf(user1: String, user2: String): Int = setOf(user1, user2).hashCode()
    }

    fun whoIsInterlocutor(): String {
        return (if (User.current == user1) user2 else user1) ?: "¿Somebody?"
    }
}
