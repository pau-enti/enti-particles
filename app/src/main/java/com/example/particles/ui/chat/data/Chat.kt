package com.example.particles.ui.chat.data

import com.google.firebase.Timestamp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

data class Chat(
    val id: String? = null,
    private val user1: String? = null,
    private val user2: String? = null,
    var name: String? = null,
    val messages: ArrayList<ChatMessage>? = null
) {

    var owner: String? = null
    var receiver: String? = if (owner == user1) user2 else user1

    fun sendMessage(message: String) {
        val newMessage = ChatMessage(owner, message, Timestamp.now())

        // Update local
        messages?.add(newMessage)

        // Update remote database
        id?.let {
            db.child(it)                                     // this chat
                .child("messages")                  // messages array
                .child("${message.length - 1}")     // message id
                .setValue(newMessage)
        }
    }

    companion object {
        private val db =
            Firebase.database("https://particles-38ca0-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("chat")

        fun openChat(id: String, owner: String, onComplete: (Chat?) -> Unit) {
            val request = db.child(id).get()

            request.addOnSuccessListener {
                val chat = try {
                    it.getValue(Chat::class.java)
                } catch (e: Exception) {
                    null
                }

                chat?.owner = owner
                onComplete(chat)
            }

            request.addOnFailureListener {
                onComplete(null)
            }
        }
    }
}