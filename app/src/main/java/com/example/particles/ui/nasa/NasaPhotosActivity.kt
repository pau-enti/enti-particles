package com.example.particles.ui.nasa

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.particles.R
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
    private lateinit var binding: ActivityNasaPhotosBinding
    private val adapter = NasaPhotosRecyclerAdapter(this)
    private val theOutside = Retrofit.Builder()
        .baseUrl("https://images-api.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNasaPhotosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.photosList.adapter = adapter

        preformSearch("eruption of sun")

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                preformSearch(query ?: "")
                binding.searchView.clearFocus() // Hide keywoard
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
        binding.searchTitle.text = "Searching... $query"
        binding.progressNasaSearch.isVisible = true

        call.enqueue(object : Callback<NasaPhotosCollection> {
            override fun onResponse(
                call: Call<NasaPhotosCollection>,
                response: Response<NasaPhotosCollection>
            ) {
                binding.progressNasaSearch.isGone = true
                binding.searchTitle.text = "Results about: $query"
                adapter.updatePhotosList(response.body()?.getPhotos())
            }

            override fun onFailure(call: Call<NasaPhotosCollection>, t: Throwable) {
                binding.progressNasaSearch.isGone = true
                binding.searchTitle.text = getString(R.string.error_on_search)
            }
        })
    }
}