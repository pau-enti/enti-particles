package com.example.particles.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.particles.databinding.ActivityChatBinding
import com.example.particles.ui.chat.data.Chat

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    private lateinit var chat: Chat
    private val adapter = ChatRecyclerViewAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.chatView.adapter = adapter

        Chat.openChat("0", "pau.garcia@enti.cat") {
            chat = it ?: Chat.createChat("0", "pau.garcia@enti.cat", "someone@enti.cat", "Tonight's party", arrayListOf())
            adapter.updateChat(chat)

            chat.onMessageReceived = {
                adapter.notifyNewMessage()
            }

            binding.chatView.scrollToPosition(chat.messages.size)
        }

        binding.messageSend.setOnClickListener {
            val message = binding.messageInput.text
            if (!message.isNullOrBlank()) {
                chat.sendMessage(message.toString())
                adapter.notifyNewMessage()

                binding.chatView.scrollToPosition(chat.messages.size)
                binding.messageInput.setText("")
            }
        }



    }
}