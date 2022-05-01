package com.example.particles.ui.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.particles.R
import com.example.particles.databinding.ItemSentMessageBinding

class ContactsRecyclerViewAdapter() :
    RecyclerView.Adapter<ContactsRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_sent_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val messages = chatViewModel.chat.value?.messages ?: return
//        holder.message.text = messages[position].content
    }

    override fun getItemCount(): Int = 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val message: TextView =
            ItemSentMessageBinding.bind(view).myMessage

    }

}