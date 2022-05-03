package com.example.particles.ui.contacts

import android.R
import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.particles.databinding.ActivityChatsListBinding
import com.example.particles.ui.chat.User


class ContactsActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatsListBinding
    private val contactsVM by viewModels<ContactsViewModel>()
    private val adapter = ContactsRecyclerViewAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.contactsRecyclerView.adapter = adapter

        contactsVM.loadContacts(this)

        contactsVM.contacts.observe(this) {
            adapter.updateContacts(it)
        }

        binding.addContactButton.setOnClickListener {
            val name = binding.newContact.text.toString()
            binding.newContact.setText("")
            contactsVM.addContact(this, Contact(name, name))
        }

        binding.userButton.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_TEXT

            dialog.setView(input)
                .setTitle("Who are you?")
                .setPositiveButton("Modify") { _, _ ->
                    User.current = input.text.toString()
                }
                .setNegativeButton("Cancel", null)
                .setIcon(R.drawable.ic_baseline_emoji_people_24)
                .show()
        }
    }
}