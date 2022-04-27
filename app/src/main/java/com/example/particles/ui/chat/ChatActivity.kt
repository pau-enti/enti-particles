package com.example.particles.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.particles.databinding.ActivityChatBinding
import com.example.particles.ui.chat.data.Chat
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    private var chat: Chat? = null
    private val adapter = ChatRecyclerViewAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.chatView.adapter = adapter

//        Chat.openChat("UfiBPE5Iut7IvlWgwJ7q", "pau.g.gozalvez@gmail.com") {
//            chat = it
//            adapter.updateChat(chat)
//            chat?.onMessageReceived = {
//                adapter.notifyMessageSent()
//            }
//        }
//
//        binding.messageSend.setOnClickListener {
//            val message = binding.messageInput.text
//            if (!message.isNullOrBlank()) {
//                chat?.sendMessage(message.toString())
//                adapter.notifyMessageSent()
//                binding.chatView.smoothScrollToPosition(chat?.messages?.size ?: 0)
//                binding.messageInput.setText("")
//            }

            val db =
                Firebase.database("https://particles-38ca0-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("chat")

//            db.setValue(
//                listOf(
//                    hashMapOf(
//                        "name" to "first chat",
//                        "user1" to "Pau",
//                        "user2" to "Yan"
//                    )
//                )
//            )

            db.child("0").get().addOnSuccessListener {
//                val chats = it.value as List<*>
//                val chat = chats[0] as Map<*, *>
//                val name = chat["name"]
//                val u1 = chat["user1"]
//                val u2 = chat["user2"]

                val c = it.getValue(Chat::class.java)
           // }
        }
    }
}