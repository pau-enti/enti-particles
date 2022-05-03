package com.example.particles.ui.contacts

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.particles.R
import com.example.particles.databinding.ItemContactBinding
import com.example.particles.ui.chat.ChatActivity

class ContactsRecyclerViewAdapter(val context: Context) :
    RecyclerView.Adapter<ContactsRecyclerViewAdapter.ViewHolder>() {

    private var contacts: List<Contact> = listOf()

    fun updateContacts(contacts: List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contacts[position]
        holder.name.text = contact.name

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(ChatActivity.EXTRA_CHAT_ID, contact.userId)
            intent.putExtra(ChatActivity.EXTRA_USER_ID, contact.userId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = contacts.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemContactBinding.bind(view)

        val name: TextView = binding.contactName
        val image: ImageView = binding.contactImage
    }

}