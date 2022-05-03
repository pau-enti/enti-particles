package com.example.particles.ui.contacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.particles.databinding.ActivityChatsListBinding

class ContactsActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatsListBinding
    private val contactsVM by viewModels<ContactsViewModel>()
    private val adapter = ContactsRecyclerViewAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.contactsRecyclerView.adapter = adapter

        contactsVM.contacts.observe(this) {
            adapter.notifyDataSetChanged()
        }

        binding.addContactButton.setOnClickListener {
            val name = binding.newContact.text.toString()
            contactsVM.addContact(Contact(name, name))
        }
    }
}