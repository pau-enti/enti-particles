package com.example.particles.ui.chat.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.reflect.safeCast

class Chat private constructor(
    val id: String,
    val ownerUserId: String,
    val receiverUserId: String,
    var name: String,
    val messages: ArrayList<ChatMessage>
) {
    val db = Firebase.firestore.collection("chats")

    fun sendMessage(message: String) {
        db.document(id).update("messages", FieldValue.arrayUnion(hashMapOf(
            "author" to ownerUserId,
            "content" to message,
            "time" to Timestamp.now()
        )))
    }

    companion object {
        private fun loadMessages(jsonArray: List<*>): ArrayList<ChatMessage> {
            return jsonArray.mapNotNull {
                val doc = Map::class.safeCast(it) ?: return@mapNotNull null
                val author = String::class.safeCast(doc.getOrDefault("author", null))
                val content = String::class.safeCast(doc.getOrDefault("content", null))
                val time = Timestamp::class.safeCast(doc.getOrDefault("time", null))

                if (author == null || time == null || content == null)
                    return@mapNotNull null

                ChatMessage(author, content, time)
            } as ArrayList
        }

        fun openChat(id: String, ownerUserId: String, onComplete: (Chat?) -> Unit) {
            val db = Firebase.firestore.collection("chats")
            val chatJson = db.document(id).get()

            chatJson.addOnSuccessListener r@{ res ->
                val u1 = res.getString("user1")
                val u2 = res.getString("user2")
                val receiver = (if (u2 == ownerUserId) u1 else u2) ?: return@r onComplete(null)
                val name = res.getString("name") ?: return@r onComplete(null)
                val messages = (List::class.safeCast(res.get("messages")))?.let {
                    loadMessages(it)
                } ?: return@r onComplete(null)

                val chat = Chat(id, ownerUserId, receiver, name, messages)
                onComplete(chat)
            }
        }
    }
}