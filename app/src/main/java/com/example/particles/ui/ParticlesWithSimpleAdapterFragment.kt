package com.example.particles.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import com.example.particles.R
import com.example.particles.data.Particles
import com.example.particles.databinding.FragmentParticlesListBinding
import com.example.particles.utils.SimpleAdapter
import kotlin.random.Random

class ParticlesWithSimpleAdapterFragment : Fragment() {

    private lateinit var binding: FragmentParticlesListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentParticlesListBinding.inflate(inflater)
        binding.searchBar.isGone = true

        val adapter = SimpleAdapter(
            Particles,
            R.layout.item_list_particle
        ) { itemView, element, i ->

            // Set name
            itemView.findViewById<TextView>(R.id.particleName).text =
                "${element.name} at position $i"

            // Set random color
            itemView.findViewById<ImageView>(R.id.particleImage).setColorFilter(Random.nextInt())
        }

        // Només utilitzo el context si no és null; sino, finalitzo activity
        context?.let {
            binding.particlesList.adapter = adapter
        } ?: activity?.finish()

        return binding.root
    }
}