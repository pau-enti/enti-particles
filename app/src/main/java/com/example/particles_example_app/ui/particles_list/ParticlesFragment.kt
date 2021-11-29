package com.example.particles_example_app.ui.particles_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.particles_example_app.R
import com.example.particles_example_app.data.Particles
import com.example.particles_example_app.databinding.FragmentParticlesBinding

class ParticlesFragment : Fragment() {

    private lateinit var binding: FragmentParticlesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentParticlesBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set the adapter
        if (view is RecyclerView) {
            context?.let {
                // Només utilitzo el context si no és null; sino, finalitzo activity
                view.adapter = ParticleRecyclerViewAdapter(it, Particles)
            } ?: activity?.finish()
        }

        return view
    }
}