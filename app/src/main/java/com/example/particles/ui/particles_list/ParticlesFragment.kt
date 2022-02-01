package com.example.particles.ui.particles_list


import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.particles.R
import com.example.particles.data.Particle
import com.example.particles.data.Particles
import com.example.particles.databinding.FragmentParticlesListBinding
import com.google.android.material.chip.Chip
import com.google.android.material.resources.TextAppearance


class ParticlesFragment : Fragment() {

    private lateinit var binding: FragmentParticlesListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        // Init
        binding = FragmentParticlesListBinding.inflate(inflater)
        val retView = binding.root
        val ctx = context ?: return retView.also { activity?.finish() }

        // Creem la llista d'elements que se suggeriran
        val suggestedValues = Particle.Family.values().map { it.name } + Particles.map { it.name }

        // Init adapter llista de particules
        binding.list.adapter = ParticleRecyclerViewAdapter(ctx, Particles)

        // Init adapter per fer la findbar
        binding.searchBar.setAdapter(
            ArrayAdapter(
                ctx,
                R.layout.item_list_text,
                suggestedValues
            )
        )

        binding.searchBar.setOnItemClickListener { parent, view, position, id ->
            val chip = Chip(ctx)
            chip.text = parent.getItemAtPosition(position).toString()
            chip.chipIcon = ContextCompat.getDrawable(ctx, R.drawable.ic_baseline_cancel_24)

            chip.setOnClickListener {
                binding.chipsGroup.removeView(it)
            }

            binding.chipsGroup.addView(chip)
            binding.searchBar.setText("")
        }


        return retView
    }


}