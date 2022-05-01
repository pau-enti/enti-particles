package com.example.particles.ui.chats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactsViewModel : ViewModel() {
    val contacts = MutableLiveData<ArrayList<ContactModel>>()

}