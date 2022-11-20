package com.example.particles.ui.quotes

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiQuotes {

    @GET("random.json/")
    fun getPhrase(): Call<Quote>

    @POST("hangman")
    fun newGame(): Call<HangmanGame>

    @GET("hangman")
    fun getSolution(@Query("token") token: String): Call<HangmanGame>

    data class HangmanGame(
        val hangman: String?,
        val token: String,
        val solution: String?
    )

}