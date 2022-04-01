package com.example.particles.ui.nasa.rest

import retrofit2.Call
import retrofit2.http.GET

interface APINasa {

    @GET("search?q=pillars%20of%20creation")
    fun getPhoto(): Call<NasaPhotosCollection>

}