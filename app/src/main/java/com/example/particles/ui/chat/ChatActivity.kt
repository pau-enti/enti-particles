package com.example.particles.ui.chat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.particles.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: ChatRecyclerViewAdapter

    private val chatViewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ChatRecyclerViewAdapter(this, binding.chatView.layoutManager)
        binding.chatView.adapter = adapter

        // TODO provisional
        val user = binding.user.text.toString()
        chatViewModel.openChat("0", user)

        chatViewModel.chat.observe(this) { chat ->
            chat ?: return@observe
            adapter.updateChat(chat)
        }

        binding.messageSend.setOnClickListener {
            val message = binding.messageInput.text

            if (!message.isNullOrBlank()) {
                val user = binding.user.text.toString()
                adapter.author = user

                chatViewModel.sendMessage(message.toString())
                adapter.notifyNewMessage()
                binding.messageInput.setText("")
            }
        }
    }
}