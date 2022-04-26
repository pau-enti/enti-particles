package com.example.particles.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.particles.databinding.ActivityChatBinding
import com.example.particles.ui.chat.data.Chat
import com.example.particles.utils.toast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var db: CollectionReference

    private var chat: Chat? = null
    private val adapter = ChatRecyclerViewAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.chatView.adapter = adapter

        db = Firebase.firestore.collection("chats")
        Chat.openChat("UfiBPE5Iut7IvlWgwJ7q", "pau.g.gozalvez@gmai.com") {
            chat = it
            adapter.updateChat(chat)
            toast("Updated!")
        }

        binding.messageSend.setOnClickListener {
            val message = binding.messageInput.text
            if (!message.isNullOrBlank()) {
                chat?.sendMessage(message.toString())
                adapter.notifyMessageSent()
                binding.chatView.smoothScrollToPosition(chat?.messages?.size ?: 0)
                binding.messageInput.setText("")
            }
        }


    }
}