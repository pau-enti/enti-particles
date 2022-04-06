package com.example.particles.ui.nasa

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.particles.databinding.ItemListNasaPhotoBinding
import com.example.particles.ui.nasa.rest.NasaPhoto
import com.squareup.picasso.Picasso

class NasaPhotosRecyclerAdapter(val context: Context) :
    RecyclerView.Adapter<NasaPhotosRecyclerAdapter.ViewHolder>() {

    private var photosList: List<NasaPhoto>? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemListNasaPhotoBinding.bind(view)

        val image = binding.nasaImage
        val title = binding.nasaImageTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemListNasaPhotoBinding.inflate(inflater, parent, false).root

        // Assignem el layout al ViewHolder
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Alternativa sense fer ús de placeholder (llavors no cal context)
//        Picasso.get().load(photosList?.get(position)?.link).into(holder.image)

        // Carreguem la imatge del link al layout
        // Tenim en compte que trigarà un temps, hi afegim un placeholder
        Picasso.Builder(context)
            .build()
            .load(photosList?.get(position)?.link)
            .into((holder.image))

        // Títol de la imatge
        holder.title.text = photosList?.get(position)?.title
    }

    override fun getItemCount(): Int {
        return photosList?.size ?: 0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatePhotosList(photosList: List<NasaPhoto>?) {
        this.photosList = photosList
        notifyDataSetChanged()
    }
}