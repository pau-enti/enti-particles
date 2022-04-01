package com.example.particles.ui.nasa

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.particles.R
import com.example.particles.databinding.ItemListNasaPhotoBinding
import com.example.particles.ui.nasa.rest.NasaPhoto

class NasaPhotosRecyclerAdapter(val context: Context) :
    RecyclerView.Adapter<NasaPhotosRecyclerAdapter.ViewHolder>() {

    private var photosList: List<NasaPhoto>? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemListNasaPhotoBinding.bind(view)

        val image = binding.nasaImage
        val title = binding.nasaImageTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val inflater = LayoutInflater.from(context)
//        val view = ItemListNasaPhotoBinding.inflate(inflater).root
//
//        return ViewHolder(view)
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_list_nasa_photo, parent, false)

        // Assignem el layout al ViewHolder
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Toast.makeText(context, "yasdgasdad", Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int {
        return 3 //photosList?.size ?: 0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatePhotosList(photosList: List<NasaPhoto>?) {
        this.photosList = photosList
        notifyDataSetChanged()
    }
}