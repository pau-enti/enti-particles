package com.example.particles.ui.chat.model

import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList


data class Chat(
    val id: String? = null,
    val user1: String? = null,
    val user2: String? = null,
    var name: String? = null,
    var messages: ArrayList<ChatMessage> = arrayListOf()
) {

    @get:Exclude
    var owner: String? = null

    @get:Exclude
    var receiver: String? = if (owner == user1) user2 else user1
        private set
}