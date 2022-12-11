package com.example.particles.ui.chat.contacts

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class ContactsViewModel : ViewModel() {
    val contacts = MutableLiveData<ArrayList<Contact>>()
    private val DATA_FILENAME = "chat_contacts.dat"

    private fun <T> MutableLiveData<T>.notifyObservers() {
        postValue(value)
    }

    private fun saveData(context: Context) {
        val data = contacts.value ?: return
        context.openFileOutput(DATA_FILENAME, Context.MODE_PRIVATE).use {
            val oos = ObjectOutputStream(it)
            oos.writeObject(data)
        }
    }

    fun loadData(context: Context) {
        try {
            context.openFileInput(DATA_FILENAME).use {
                val iin = ObjectInputStream(it)
                val data = iin.readObject() ?: return@use

                if (data is ArrayList<*> ) {
                    contacts.value = data.filterIsInstance<Contact>() as ArrayList
                }
            }
        } catch (_: Exception) {
            // pass
        }

        if (contacts.value == null)
            contacts.value = arrayListOf()
    }


    fun addContact(context: Context, contact: Contact) {
        contacts.value?.add(contact)
        contacts.notifyObservers()
        saveData(context)
    }

    fun deleteContact(context: Context, contact: Contact) {
        contacts.value?.remove(contact)
        contacts.notifyObservers()
        saveData(context)
    }
}