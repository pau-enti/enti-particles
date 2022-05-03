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

    var messagesAuthor: String? = null

    fun createChat(chat: Chat, owner: String) {
        messagesAuthor = owner
        chat.id?.let {
            db.child(it.toString()).setValue(chat) // insert it on the db
        }
    }

    fun sendMessage(message: String) {
        val cht = chat.value ?: return
        val id = cht.id ?: return
        val newMessage = ChatMessage(messagesAuthor, message, System.currentTimeMillis())

        // Update local
        cht.messages.add(newMessage)

        // Update remote database
        db.child(id.toString())                                     // this chat
            .child("messages")                  // messages array
            .child("${cht.messages.size - 1}")     // message id
            .setValue(newMessage)
    }

    fun openChat(id: String, ownerId: String) {
        val request = db.child(id).get()

        // TODO onFailure is not caught
        request.addOnSuccessListener {
            if (it == null)
            //createChat()
                TODO() // create chat

            it.getValue(Chat::class.java)?.apply {
                messagesAuthor = ownerId
                chat.postValue(this)
                subscribe(this)
            }
        }
    }

    private fun subscribe(cht: Chat) {
        val id = cht.id ?: return

        db.child(id.toString()).addValueEventListener(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) = Unit

            override fun onDataChange(snapshot: DataSnapshot) {

                // TODO we should update all fields
                val typeIndicator = object : GenericTypeIndicator<ArrayList<ChatMessage>>() {}
                val dataBaseMessages = snapshot.child("messages").getValue(typeIndicator)
                dataBaseMessages?.let {
                    cht.messages = it
                }

                chat.postValue(cht)
            }
        })
    }
}