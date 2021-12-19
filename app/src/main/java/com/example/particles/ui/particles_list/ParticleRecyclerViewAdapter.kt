package com.example.particles.ui.particles_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.particles.R
import com.example.particles.data.Particle
import com.example.particles.databinding.ItemListParticleBinding
import com.example.particles.utils.toast


/**
 * Adapter que presenta una llista de particules
 */
class ParticleRecyclerViewAdapter(val context: Context, private val particles: List<Particle>) :
    RecyclerView.Adapter<ParticleRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_list_particle, parent, false)

        // Assignem el layout al ViewHolder
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val particle = particles[position]

        // Set name
        holder.name.text = particle.name

        // Set color
        // It selects the ID of the color in function of the family of this particle
        val color = when (particle.family) {
            Particle.Family.QUARK -> R.color.quarks
            Particle.Family.LEPTON -> R.color.leptons
            Particle.Family.GAUGE_BOSON -> R.color.gauge_bosons
            Particle.Family.SCALAR_BOSON-> R.color.higgs
        }
        // It converts the ID to the properly color and set it to the image
        holder.image.setColorFilter(context.getColor(color))

        // Set on item click listener
        holder.view.setOnClickListener {
            // Sabem que es mostrarà "bé" perquè Particle és un data class
            context.toast(particle.toString())
        }
    }

    override fun getItemCount(): Int = particles.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemListParticleBinding.bind(view)

        val name: TextView = binding.particleName
        val image: ImageView = binding.particleImage
    }
}