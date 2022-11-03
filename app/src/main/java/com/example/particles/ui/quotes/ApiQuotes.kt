package com.example.particles.ui.quotes

import retrofit2.Call
import retrofit2.http.GET

interface ApiQuotes {

    @GET("random.json/")
    fun getPhrase(): Call<Quote>

}