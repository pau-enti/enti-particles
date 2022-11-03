package com.example.particles.ui.chat.contacts

import java.io.Serializable
import java.net.URI

data class Contact(
    val name: String,
    val userId: String,
    val imageFile: URI?=null
): Serializable