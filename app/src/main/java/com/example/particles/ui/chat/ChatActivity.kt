package com.example.particles.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.particles.databinding.ActivityChatBinding
import com.example.particles.utils.toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database.reference

        binding.messageSend.setOnClickListener {
            database.child("users").child("user1").setValue("Gerard")
            database.child("glossary").setValue("Gerard4")

            database.child("glossary").get().addOnCompleteListener {
                toast(it.result)
            }

            database.push()

        }

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
//                val post = dataSnapshot.getValue<Post>()
                // ...
                toast(dataSnapshot.value)
                println("ARAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                toast(databaseError.message)
            }
        }

        database.child("glossary").addValueEventListener(postListener)
    }
}