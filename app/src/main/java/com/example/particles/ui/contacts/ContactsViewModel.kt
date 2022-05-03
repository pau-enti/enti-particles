package com.example.particles.ui.contacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class ContactsViewModel : ViewModel() {
    val contacts = MutableLiveData<ArrayList<Contact>>()
    private val dataFilename = "contacts.dat"

    private fun saveContacts() {
        val fos = FileOutputStream(dataFilename)
        val oos = ObjectOutputStream(fos)

        oos.writeObject(contacts.value)
        oos.close()
    }

    @Suppress("UNCHECKED_CAST")
    fun loadContacts() {
        val fin = FileInputStream(dataFilename)
        val oin = ObjectInputStream(fin)

        val readContacts = oin.readObject() as ArrayList<Contact>
        oin.close()
        contacts.postValue(readContacts)
    }

    fun addContact(contact: Contact) {
        contacts.value?.add(contact)
        saveContacts()
    }
}