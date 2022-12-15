package com.example.particles.ui.chat.chat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.example.particles.databinding.ActivityChatBinding
import com.example.particles.utils.toast

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: ChatRecyclerViewAdapter

    private val chatViewModel: ChatViewModel by viewModels()

    companion object {
        const val EXTRA_USER_ID = "EXTRA_USER_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ChatRecyclerViewAdapter(this, chatViewModel)
        binding.chatView.adapter = adapter

        val user = intent.extras?.getString(EXTRA_USER_ID) ?: return
        supportActionBar?.title = user
        chatViewModel.openChatWith(user)

        chatViewModel.chat.observe(this) {
            binding.progressBar.isGone = true

            if (it == null) {
                toast("Chat not found :(")
                return@observe finish()
            }
        }

        binding.sendButton.setOnClickListener {
            val message = binding.messageInput.text

            if (!message.isNullOrBlank()) {
                chatViewModel.sendMessage(message.toString())
                binding.messageInput.setText("")
            }
        }
    }
}