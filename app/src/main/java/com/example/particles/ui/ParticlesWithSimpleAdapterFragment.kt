package com.example.particles.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.particles.R
import com.example.particles.data.Particles
import com.example.particles.utils.SimpleAdapter
import kotlin.random.Random

class ParticlesWithSimpleAdapterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val adapter = SimpleAdapter(
            Particles,
            R.layout.fragment_particles_list,
            R.layout.item_list_particle,
            inflater, container
        ) { itemView, element, i ->

            // Set name
            itemView.findViewById<TextView>(R.id.particleName).text =
                "${element.name} at position $i"

            // Set random color
            itemView.findViewById<ImageView>(R.id.particleImage).setColorFilter(Random.nextInt())
        }

        return adapter.view
    }
}