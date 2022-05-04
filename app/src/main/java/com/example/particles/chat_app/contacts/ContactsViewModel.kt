package com.example.particles.chat_app.contacts

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class ContactsViewModel : ViewModel() {
    val contacts = MutableLiveData<ArrayList<Contact>>()
    private val dataFilename = "chat_contacts.dat"

    private fun MutableLiveData<*>.notifyObservers() {
        value = value // xd
    }

    private fun saveContacts(context: Context) {
        val data = contacts.value ?: return
        context.openFileOutput(dataFilename, Context.MODE_PRIVATE).use {
            val oos = ObjectOutputStream(it)
            oos.writeObject(data)
            oos.close()
        }
    }

    fun loadContacts(context: Context) {
        try {
            context.openFileInput(dataFilename).use {
                val iin = ObjectInputStream(it)
                val data = iin.readObject() ?: return@use

                @Suppress("UNCHECKED_CAST")
                val readContacts = data as ArrayList<Contact>

                contacts.value = readContacts
                iin.close()
            }
        } catch (e: Exception) {
            // pass
        }

        if (contacts.value == null)
            contacts.value = arrayListOf()
    }


    fun addContact(context: Context, contact: Contact) {
        contacts.value?.add(contact)
        contacts.notifyObservers()
        saveContacts(context)
    }
}