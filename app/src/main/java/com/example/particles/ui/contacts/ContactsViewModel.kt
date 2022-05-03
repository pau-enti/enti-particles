package com.example.particles.ui.contacts

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.FileNotFoundException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class ContactsViewModel : ViewModel() {
    val contacts = MutableLiveData<ArrayList<Contact>>()
    private val dataFilename = "chat_contacts.dat"

    private fun saveContacts(context: Context) {
        val data = contacts.value ?: return
        context.openFileOutput(dataFilename, Context.MODE_PRIVATE).use {
            val oos = ObjectOutputStream(it)
            oos.writeObject(data)
            oos.close()
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun loadContacts(context: Context) {
        try {
            context.openFileInput(dataFilename).use {
                val iin = ObjectInputStream(it)
                val data = iin.readObject() ?: return
                val readContacts = data as ArrayList<Contact>
                contacts.postValue(readContacts)
                iin.close()
            }
        } catch (e: FileNotFoundException) {
            // pass
        }
    }

    fun addContact(context: Context, contact: Contact) {
        contacts.value?.add(contact)
        saveContacts(context)
    }
}