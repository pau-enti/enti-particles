package com.example.particles.ui.nasa.rest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APINasa {

    @GET("search")
    fun getPhoto(@Query("q") query: String): Call<NasaPhotos>

}