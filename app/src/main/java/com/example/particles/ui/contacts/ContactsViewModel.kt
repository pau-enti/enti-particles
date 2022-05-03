package com.example.particles.ui.contacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactsViewModel : ViewModel() {
    val contacts = MutableLiveData<ArrayList<Contact>>()

}