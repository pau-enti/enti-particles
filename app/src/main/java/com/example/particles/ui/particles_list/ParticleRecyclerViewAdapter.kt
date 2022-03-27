package com.example.particles.ui.particles_list

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.particles.ui.ParticleEditActivity
import com.example.particles.ui.ParticleEditActivity.Companion.INTENT_EXTRA_PARTICLE_ID
import com.example.particles.R
import com.example.particles.data.Particle
import com.example.particles.databinding.ItemListParticleBinding


/**
 * Adapter que presenta una llista de particules
 */
class ParticleRecyclerViewAdapter(val context: Context, val particles: ArrayList<Particle>) :
    RecyclerView.Adapter<ParticleRecyclerViewAdapter.ViewHolder>() {

    private var modifiedElement: Int? = null

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
        // It converts the ID to the properly color and set it to the image
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        if (prefs.getBoolean("colorParticles", true))
            holder.image.setColorFilter(context.getColor(particle.family.color()))
        else
            holder.image.setColorFilter(context.getColor(R.color.gray))

        // Set on item click listener
        holder.view.setOnClickListener {
            // Sabem que es mostrarà "bé" perquè Particle és un data class
//            context.toast(particle.toString())

            modifiedElement = holder.absoluteAdapterPosition

            val intent = Intent(context, ParticleEditActivity::class.java)
            intent.putExtra(INTENT_EXTRA_PARTICLE_ID, modifiedElement)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = particles.size

    fun notifyParticleUpdated() {
        modifiedElement?.let {
            notifyItemChanged(it)
        }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemListParticleBinding.bind(view)

        val name: TextView = binding.particleName
        val image: ImageView = binding.particleImage
    }
}