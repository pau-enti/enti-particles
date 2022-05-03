package com.example.particles.ui.contacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.particles.databinding.ActivityChatsListBinding

class ContactsActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatsListBinding
    private val contactsVM by viewModels<ContactsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.contactsRecyclerView.adapter = ContactsRecyclerViewAdapter(this)
    }
}