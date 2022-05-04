package com.example.particles.chat_app.notifications

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.particles.chat_app.User
import com.example.particles.chat_app.chat.model.Chat
import com.example.particles.chat_app.contacts.Contact

class NotificationsViewModel : ViewModel() {
    val chats = ArrayList<MutableLiveData<Chat>>()
    val newData = MutableLiveData<Unit>()

    fun subscribe(chts: ArrayList<Contact>) {
        chts.forEach {
            chats.add(MutableLiveData(Chat(User.current, it.userId)))
        }
        newData.postValue(Unit)
    }

}