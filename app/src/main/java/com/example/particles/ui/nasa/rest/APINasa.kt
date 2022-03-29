package com.example.particles.ui.nasa.rest

import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url

interface APINasa {

//    @GET("search?q=pillars%20of%20creation")
//    fun getPhoto(): Call<Any>

      @Headers("Accept: application/json", "X-TheySaidSo-Api-Secret:paugarcia95")
//      @GET("quote/random")
      @GET("qod/")
      fun getPhrase(): Call<QuotesResponse>

      data class QuotesResponse(val contents: Quotes)
      data class Quotes(val quotes: List<Quote>)
      data class Quote(val author: String, val quote: String)


}