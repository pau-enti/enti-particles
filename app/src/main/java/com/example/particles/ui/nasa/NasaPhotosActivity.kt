package com.example.particles.ui.nasa

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.particles.R
import com.example.particles.databinding.ActivityNasaPhotosBinding
import com.example.particles.ui.nasa.rest.APINasa
import com.example.particles.ui.nasa.rest.NasaPhoto
import com.example.particles.ui.nasa.rest.Photos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NasaPhotosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNasaPhotosBinding
    private val adapter = NasaPhotosRecyclerAdapter(this)
    private val theOutside = Retrofit.Builder()
        .baseUrl("https://images-api.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityNasaPhotosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.photosList.adapter = adapter

        preformSearch("Milky Way")

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                preformSearch(query ?: "")
                binding.searchView.clearFocus() // Hide keywoard
                binding.photosList.smoothScrollToPosition(0)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // do nothing
                return true
            }

        })
    }

    private fun preformSearch(query: String) {
        val call = theOutside.create(APINasa::class.java).getPhoto(query)
        binding.searchTitle.text = query
        binding.searchTitle.isGone = true
        binding.progressNasaSearch.isVisible = true

        call.enqueue(object : Callback<Photos> {
            override fun onResponse(
                call: Call<Photos>,
                response: Response<Photos>
            ) {
                binding.progressNasaSearch.isGone = true

                val res = response.body()?.collection?.items?.map {
                    NasaPhoto(it.data[0].title, it.data[0].description, "", it.links[0].href)
                }

                if (res?.isNotEmpty() == true)
                    adapter.updatePhotosList(res)
                else {
                    binding.searchTitle.text = "The search \"$query\" produced 0 results!"
                    binding.searchTitle.isGone = false
                }
            }

            override fun onFailure(call: Call<Photos>, t: Throwable) {
                binding.progressNasaSearch.isGone = true
                binding.searchTitle.text = getString(R.string.error_on_search)
                binding.searchTitle.isGone = false
            }
        })
    }
}