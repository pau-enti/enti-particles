package com.example.particles.ui.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.particles.R
import com.example.particles.databinding.ItemReceivedMessageBinding
import com.example.particles.databinding.ItemSentMessageBinding
import com.example.particles.ui.chat.data.Chat


class ChatRecyclerViewAdapter(val context: Context, private var chat: Chat? = null) :
    RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder>() {

    fun updateChat(chat: Chat) {
        this.chat = chat
        notifyDataSetChanged()
    }

    fun notifyMessageSent() {
        chat?.messages?.let {
            notifyItemInserted(it.size - 1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val view = if (viewType == SENT_TYPE)
            layoutInflater.inflate(R.layout.item_sent_message, parent, false)
        else
            layoutInflater.inflate(R.layout.item_received_message, parent, false)

        return ViewHolder(view, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        // 0 -> sent message
        // 1 -> received message
        return if (chat?.messages?.get(position)?.author == chat?.owner) SENT_TYPE else RECEIVED_TYPE
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.message.text = chat?.messages?.get(position)?.content
    }

    override fun getItemCount(): Int = chat?.messages?.size ?: 0

    inner class ViewHolder(view: View, type: Int) : RecyclerView.ViewHolder(view) {
        val message: TextView = if (type == SENT_TYPE)
            ItemSentMessageBinding.bind(view).myMessage
        else
            ItemReceivedMessageBinding.bind(view).othersMessage
    }

    companion object {
        const val SENT_TYPE = 0
        const val RECEIVED_TYPE = 1
    }
}