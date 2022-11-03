package com.example.particles.ui.chat.notifications

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.particles.ui.chat.User
import com.example.particles.ui.chat.chat.model.Chat
import com.example.particles.ui.chat.contacts.ContactsViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NotificationsViewModel : ViewModel() {
    val contactsVM = ContactsViewModel()

    val chats = ArrayList<MutableLiveData<Chat>>()

    private val db =
        Firebase.database("https://particles-38ca0-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("chats")

    fun subscribeCurrentContacts(context: Context): ArrayList<MutableLiveData<Chat>> {
        contactsVM.loadContacts(context)
        contactsVM.contacts.value?.forEachIndexed { index, contact ->
            val subscription = MutableLiveData(Chat(User.current, contact.userId))
            chats.add(subscription)
            subscribe(index)
        }
        return chats
    }

    private fun subscribe(chatAtIndex: Int) {
        val chat = chats.getOrNull(chatAtIndex) ?: return
        val id = chat.value?.id ?: return

        db.child(id.toString()).addValueEventListener(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) = Unit

            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue(Chat::class.java) ?: return
                chat.postValue(data)
            }
        })
    }

}