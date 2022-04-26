package com.example.particles.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.particles.databinding.ActivityChatBinding
import com.example.particles.ui.chat.data.Chat
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var db: CollectionReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Firebase.firestore.collection("chats")
        Chat.openChat("UfiBPE5Iut7IvlWgwJ7q", "pau.g.gozalvez@gmai.com") {
            print(it)
        }

        binding.messageSend.setOnClickListener {

        }


    }
}