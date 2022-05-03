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
    val chat = MutableLiveData<Chat?>()

    private val db =
        Firebase.database("https://particles-38ca0-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("chats")

    private fun createChat(user: String) {
        val cht = Chat(User.current, user, "Chat of ${User.current} with $user")
        db.child(cht.id.toString()).setValue(cht) // insert it on the db
        chat.postValue(cht)
        subscribe(cht)
    }

    fun sendMessage(message: String) {
        val cht = chat.value ?: return
        val id = cht.id ?: return
        val newMessage = ChatMessage(User.current, message, System.currentTimeMillis())

        // Update local
        cht.messages.add(newMessage)

        // Update remote database
        db.child(id.toString())                                     // this chat
            .child("messages")                  // messages array
            .child("${cht.messages.size - 1}")     // message id
            .setValue(newMessage)
    }

    fun openChat(user: String) {
        val id = Chat.idChatOf(User.current, user).toString()
        val request = db.child(id).get()

        // TODO onFailure is not caught
        request.addOnSuccessListener {
            if (it.value == null)
                return@addOnSuccessListener createChat(user)

            it.getValue(Chat::class.java)?.apply {
                chat.postValue(this)
                subscribe(this)
            }
        }

        request.addOnFailureListener {
            createChat(user)
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