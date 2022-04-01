package com.example.particles.ui.nasa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.particles.databinding.ActivityNasaPhotosBinding
import com.example.particles.ui.nasa.rest.APINasa
import com.example.particles.ui.nasa.rest.NasaPhotosCollection
import com.example.particles.utils.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NasaPhotosActivity : AppCompatActivity() {
    lateinit var binding: ActivityNasaPhotosBinding

    val adapter = NasaPhotosRecyclerAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNasaPhotosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.photosList.adapter = adapter

        binding.buttonAPI.setOnClickListener {
            val outside = Retrofit.Builder()
                .baseUrl("https://images-api.nasa.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val call = outside.create(APINasa::class.java).getPhoto()

            println("New Quote:")
            call.enqueue(object : Callback<NasaPhotosCollection> {
                override fun onResponse(call: Call<NasaPhotosCollection>, response: Response<NasaPhotosCollection>) {
                    println(response.body()?.getPhotos()?.get(0) ?: "error produced")
                    adapter.updatePhotosList(response.body()?.getPhotos())
//                    Toast.makeText(this@NASAPhotosActivity, response.body()?.getPhotos()?.get(0).toString(), Toast.LENGTH_SHORT).show()
//                    Picasso.get().load(response.body()?.getPhotos()?.get(2)?.link).into(binding.imageView)
                }

                override fun onFailure(call: Call<NasaPhotosCollection>, t: Throwable) {
                    println(t)
                    toast("error")
                }
            })
        }


        binding.searchView.setOnCloseListener {
            toast(binding.searchView.query)
            return@setOnCloseListener true
        }
    }
}