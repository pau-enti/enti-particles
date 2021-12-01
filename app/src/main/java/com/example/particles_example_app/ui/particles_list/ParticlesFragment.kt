package com.example.particles_example_app.ui.particles_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.particles_example_app.data.Particles
import com.example.particles_example_app.databinding.FragmentParticlesListBinding

class ParticlesFragment : Fragment() {

    private lateinit var binding: FragmentParticlesListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentParticlesListBinding.inflate(inflater)
        val view = binding.root

        // Només utilitzo el context si no és null; sino, finalitzo activity
        context?.let {
            view.adapter = ParticleRecyclerViewAdapter(it, Particles)
        } ?: activity?.finish()

        return view
    }
}