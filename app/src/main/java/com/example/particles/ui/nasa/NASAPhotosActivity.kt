package com.example.particles.ui.nasa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.particles.databinding.ActivityNasaPhotosBinding
import com.example.particles.ui.nasa.rest.APINasa
import com.example.particles.ui.nasa.rest.NasaPhoto
import com.example.particles.utils.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NASAPhotosActivity : AppCompatActivity() {
    lateinit var binding: ActivityNasaPhotosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNasaPhotosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAPI.setOnClickListener {
            val outside = Retrofit.Builder()
//                .baseUrl("https://images-api.nasa.gov/")
                .baseUrl("https://quotes.rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val call = outside.create(APINasa::class.java).getPhrase()
            println("YASYHASA")
            call.enqueue(object : Callback<APINasa.QuotesResponse> {
                override fun onResponse(call: Call<APINasa.QuotesResponse>, response: Response<APINasa.QuotesResponse>) {
                    println(response.body()?.contents?.quotes ?: "nothing")
                    println("YEEEEEEEEEEEEEEEEEEEE")
                }

                override fun onFailure(call: Call<APINasa.QuotesResponse>, t: Throwable) {
                    println(t)
                }
            })
        }


        binding.searchView.setOnCloseListener {
            toast(binding.searchView.query)
            return@setOnCloseListener true
        }
    }
}