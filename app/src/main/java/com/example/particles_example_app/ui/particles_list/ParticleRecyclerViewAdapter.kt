package com.example.particles_example_app.ui.particles_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.particles_example_app.R
import com.example.particles_example_app.data.Particle


/**
 * Adapter que presenta una llista de particules
 */
class ParticleRecyclerViewAdapter(private val particles: List<Particle>) :
    RecyclerView.Adapter<ParticleRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = particles[position]
        holder.idView.text = item.name
        holder.contentView.text = ""//item.mass.toString()
    }

    override fun getItemCount(): Int = particles.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.name)
        val contentView: TextView = view.findViewById(R.id.family)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}