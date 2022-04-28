package com.example.particles.ui.chat.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Exclude
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

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

    @get:Exclude
    var onMessageReceived: ((Chat) -> Unit)? = null

    init {
        if (id != null) {
            db.child(id).addValueEventListener(object : ValueEventListener {

                @Suppress("UNCHECKED_CAST")
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.child("messages").getValue(ArrayList::class.java)?.let {
                        // TODO we should update all fields
                        messages = it as ArrayList<ChatMessage>
                    }
                    onMessageReceived?.invoke(this@Chat)
                }

                override fun onCancelled(error: DatabaseError) {
                    // nothing
                }
            })
        }
    }

    fun sendMessage(message: String) {
        val newMessage = ChatMessage(owner, message, System.currentTimeMillis())

        // Update local
        messages.add(newMessage)

        // Update remote database
        id?.let {
            db.child(it)                                     // this chat
                .child("messages")                  // messages array
                .child("${messages.size - 1}")     // message id
                .setValue(newMessage)
        }
    }

    companion object {
        private val db =
            Firebase.database("https://particles-38ca0-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("chats")

        fun createChat(
            id: String,
            user1: String,
            user2: String,
            name: String,
            messages: ArrayList<ChatMessage>
        ): Chat {
            val chat = Chat(id, user1, user2, name, messages)
            db.child(id).setValue(chat) // insert it on the db
            return chat
        }

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