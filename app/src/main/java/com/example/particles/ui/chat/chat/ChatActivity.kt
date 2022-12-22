package com.example.particles.ui.chat.chat

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import com.example.particles.R
import com.example.particles.databinding.ActivityChatBinding
import com.example.particles.utils.toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: ChatRecyclerViewAdapter

    private val chatViewModel: ChatViewModel by viewModels()
    private lateinit var sendSoundPlayer: MediaPlayer
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    companion object {
        const val EXTRA_USER_ID = "EXTRA_USER_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAnalytics = Firebase.analytics
        sendSoundPlayer = MediaPlayer.create(this, R.raw.water_bloop_drop)

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

        binding.sendButton.isSoundEffectsEnabled = false
        binding.sendButton.setOnClickListener {
            val message = binding.messageInput.text

            if (!message.isNullOrBlank()) {
                sendSoundPlayer.start()

                chatViewModel.sendMessage(message.toString())
                binding.messageInput.setText("")

                firebaseAnalytics.logEvent(
                    FirebaseAnalytics.Event.LEVEL_START,

                    bundleOf(
                        FirebaseAnalytics.Param.CHARACTER to supportActionBar?.title,
                        FirebaseAnalytics.Param.ITEM_NAME to message,
                        FirebaseAnalytics.Param.SCORE to message.length
                    ))
            }
        }
    }
}