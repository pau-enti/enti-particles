package com.example.particles.ui.chat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.particles.databinding.ActivityChatBinding
import com.example.particles.ui.chat.model.Chat

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private val adapter = ChatRecyclerViewAdapter(this)

    private val chatViewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.chatView.adapter = adapter

        // TODO provisional
        val user = binding.user.text.toString()

        chatViewModel.openChat("0", user) {
            if (it != null) {
                chat = it
            } else {
                chat = Chat("0", user, "someone@enti.cat", "Tonight's party")
                chatViewModel.createChat(chat, user)
            }

            adapter.updateChat(chat)

            chat.onMessageReceived = {
                adapter.notifyNewMessage()
            }

            binding.chatView.scrollToPosition(chat.messages.size)
        }

        binding.messageSend.setOnClickListener {
            val message = binding.messageInput.text

            if (!message.isNullOrBlank()) {
                chatViewModel.sendMessage(chat, message.toString())
                adapter.notifyNewMessage()

                binding.chatView.scrollToPosition(chat.messages.size)
                binding.messageInput.setText("")
            }
        }



    }
}