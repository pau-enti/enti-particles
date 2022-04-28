package com.example.particles.ui.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.particles.ui.chat.model.Chat
import com.example.particles.ui.chat.model.ChatMessage
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatViewModel : ViewModel() {
    val chat = MutableLiveData<Chat>()

    private val db =
        Firebase.database("https://particles-38ca0-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("chats")

    fun createChat(chat: Chat, owner: String) {
        chat.owner = owner
        chat.id?.let {
            db.child(it).setValue(chat) // insert it on the db
        }
    }

    fun sendMessage(chat: Chat, message: String) {
        val newMessage = ChatMessage(chat.owner, message, System.currentTimeMillis())

        // Update local
        chat.messages.add(newMessage)

        // Update remote database
        chat.id?.let {
            db.child(it)                                     // this chat
                .child("messages")                  // messages array
                .child("${chat.messages.size - 1}")     // message id
                .setValue(newMessage)
        }
    }

    fun openChat(id: String, owner: String) {
        val request = db.child(id).get()

        // TODO onFailure is not caught
        request.addOnSuccessListener {
            try {
                it.getValue(Chat::class.java)?.apply {
                    this.owner = owner
                    chat.postValue(this)
                }
            } catch (e: Exception) {
            }
        }
    }

    fun subscribe(chat: Chat) {
        if (chat.id != null) {
            db.child(chat.id).addValueEventListener(object : ValueEventListener {

                @Suppress("UNCHECKED_CAST")
                override fun onDataChange(snapshot: DataSnapshot) {
                    val typeIndicator: GenericTypeIndicator<ArrayList<ChatMessage>> =
                        object : GenericTypeIndicator<ArrayList<ChatMessage>>() {}

                    (snapshot.child("messages").getValue(typeIndicator))?.let {
                        // TODO we should update all fields
                        chat.messages = it
                    }
                    onMessageReceived?.invoke(this@Chat)
                }

                override fun onCancelled(error: DatabaseError) {
                    // nothing
                }
            })
        }
    }
}