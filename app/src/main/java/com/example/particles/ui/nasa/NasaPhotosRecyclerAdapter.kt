package com.example.particles.ui.nasa

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ablanco.zoomy.Zoomy
import com.example.particles.R
import com.example.particles.databinding.ItemListNasaPhotoBinding
import com.example.particles.ui.nasa.rest.NasaPhoto
import com.squareup.picasso.Picasso

class NasaPhotosRecyclerAdapter(val activity: Activity) :
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Alternativa sense fer ús de placeholder (llavors no cal context)
//        Picasso.get().load(photosList?.get(position)?.link).into(holder.image)

        // Carreguem la imatge del link al layout
        // Tenim en compte que trigarà un temps, hi afegim un placeholder
        Picasso.Builder(activity)
            .build()
            .load(photosList?.get(position)?.link)
            .placeholder(R.drawable.image_placeholder)
            .into((holder.image))

        // Títol de la imatge
        holder.title.text = photosList?.get(position)?.title

        val builder: Zoomy.Builder = Zoomy.Builder(activity).target(holder.image)
        builder.register()
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