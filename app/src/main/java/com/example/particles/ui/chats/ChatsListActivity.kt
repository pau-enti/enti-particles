package com.example.particles.ui.chats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.particles.databinding.ActivityChatsListBinding

class ChatsListActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}