package com.example.particles.chat_app.notifications

import androidx.lifecycle.LifecycleService
import com.example.particles.chat_app.chat.ChatViewModel
import com.example.particles.chat_app.contacts.ContactsViewModel

class NotificationsService: LifecycleService() {

    val contactsVM = ContactsViewModel()
    val notisVM = NotificationsViewModel()

    override fun onCreate() {
        super.onCreate()

        contactsVM.loadContacts(this)
        contactsVM.contacts.observe(this) {
            notisVM.subscribe(it)
        }


    }
}