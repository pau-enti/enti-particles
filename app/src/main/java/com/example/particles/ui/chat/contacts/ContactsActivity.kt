package com.example.particles.ui.chat.contacts

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.particles.R
import com.example.particles.databinding.ActivityContactsBinding
import com.example.particles.ui.chat.User
import com.example.particles.ui.chat.notifications.NotificationsService
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds


class ContactsActivity : AppCompatActivity() {

    lateinit var binding: ActivityContactsBinding
    private val contactsVM by viewModels<ContactsViewModel>()
    private lateinit var adapter: ContactsRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ContactsRecyclerViewAdapter(this, contactsVM)
        binding.contactsRecyclerView.adapter = adapter

        // Init ads
        MobileAds.initialize(this)

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
            input.setText(User.current)

            dialog.setView(input)
                .setTitle("Who are you?")
                .setPositiveButton("Modify") { _, _ ->
                    User.current = input.text.toString()
                }
                .setNegativeButton("Cancel", null)
                .setIcon(R.drawable.ic_baseline_emoji_people_24)
                .show()
        }

        // Start notifications service
        startService(Intent(this, NotificationsService::class.java))
    }


    override fun onResume() {
        super.onResume()
        adapter.loadAd()

        val request =AdRequest.Builder().build()
        binding.adView.loadAd(request)
    }
}