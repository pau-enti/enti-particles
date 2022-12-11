package com.example.particles.ui.chat.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.particles.ui.chat.User
import com.example.particles.ui.chat.chat.model.Chat
import com.example.particles.ui.chat.chat.model.ChatMessage
import com.google.firebase.auth.FirebaseAuth
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

    private val currentUser: String
        get() = FirebaseAuth.getInstance().currentUser?.email
            ?: throw IllegalStateException("Not logged in")

    private fun createChatWith(user: String) {
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

    fun openChatWith(user: String) {
        val id = Chat.idChatOf(currentUser, user).toString()
        val request = db.child(id).get()

        request.addOnSuccessListener {
            val data = it.value

            if (data == null || data !is Chat)
                return@addOnSuccessListener createChatWith(user)

            chat.postValue(data!!)
            subscribe(data)
        }

        request.addOnFailureListener {
            createChatWith(user)
        }
    }

    private fun subscribe(cht: Chat) {
        val id = cht.id ?: return

        db.child(id.toString()).addValueEventListener(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) = Unit

            override fun onDataChange(snapshot: DataSnapshot) {
                val typeIndicator = object : GenericTypeIndicator<ArrayList<ChatMessage>>() {}
                val messages = snapshot.child("messages").getValue(typeIndicator)

                if (messages != null) {
                    cht.messages = messages
                } // else { some error happened }

                chat.postValue(cht)
            }
        })
    }
}