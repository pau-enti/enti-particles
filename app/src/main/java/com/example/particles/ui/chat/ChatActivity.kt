package com.example.particles.ui.chat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.example.particles.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: ChatRecyclerViewAdapter

    private val chatViewModel: ChatViewModel by viewModels()

    companion object {
        const val EXTRA_USER_ID = "EXTRA_USER_ID"
        const val EXTRA_CHAT_ID = "EXTRA_CHAT_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ChatRecyclerViewAdapter(this, chatViewModel, binding.chatView.layoutManager)
        binding.chatView.adapter = adapter

        // TODO provisional
        val user = intent.extras?.getString(EXTRA_USER_ID) ?: return
        val chatId = intent.extras?.getString(EXTRA_CHAT_ID) ?: return
        chatViewModel.openChat(chatId, user)

        chatViewModel.chat.observe(this) {
            binding.progressBar.isGone = true
        }

        binding.messageSend.setOnClickListener {
            val message = binding.messageInput.text

            if (!message.isNullOrBlank()) {
                chatViewModel.messagesAuthor = user
                chatViewModel.sendMessage(message.toString())
                adapter.notifyNewMessage()
                binding.messageInput.setText("")
            }
        }
    }
}