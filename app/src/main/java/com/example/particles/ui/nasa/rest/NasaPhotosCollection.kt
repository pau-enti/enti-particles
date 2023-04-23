package com.example.particles.ui.nasa.rest

import com.google.gson.annotations.SerializedName
import java.lang.Exception

// OLD: Manual way
@Deprecated("Use @NasaPhotos instead")
data class NasaPhotosCollection(@SerializedName("collection") val data: PhotosList) {
    data class PhotosList(@SerializedName("items") val photosList: List<InternalPhoto>)
    data class InternalPhoto(@SerializedName("links") val links: List<Link>, val data: List<Data>)
    data class Link(@SerializedName("href") val link: String)
    data class Data(val title: String, val description: String, val location: String?)

    fun getPhotos(): List<NasaPhoto> {
        return data.photosList.mapNotNull {
            try {
                val d = it.data[0]
                NasaPhoto(d.title, d.description, d.location ?: "No specified", it.links[0].link)
            } catch (e: Exception) {
                null
            }
        }
    }
}
