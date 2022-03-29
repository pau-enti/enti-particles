package com.example.particles.ui.nasa.rest

import com.google.gson.annotations.SerializedName

data class Collection(@SerializedName("collection") val data: PhotosList)
data class PhotosList(@SerializedName("items") val items: List<NasaPhoto>)
data class NasaPhoto(@SerializedName("collection") val data: String)