package com.example.particles.ui.particles


import android.annotation.SuppressLint
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
import kotlin.reflect.safeCast


class ParticlesFragment : Fragment() {

    private lateinit var binding: FragmentParticlesListBinding
    private val filters = ArrayList<String>()
    private val particles = ArrayList<Particle>().apply { addAll(Particles) }

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
        binding.particlesList.adapter = ParticleRecyclerViewAdapter(ctx, particles)

        // Init adapter per fer la findbar
        binding.searchBar.setAdapter(
            ArrayAdapter(
                ctx,
                R.layout.item_list_text,
                suggestedValues
            )
        )

        binding.searchBar.setOnItemClickListener { parent, _, position, _ ->

            // Obtenim el text de l'element seleccionat a la llista
            val selection = parent.getItemAtPosition(position).toString()

            // Creem un Chip dinàmicament
            val chip = Chip(ctx)
            chip.text = selection
            chip.chipIcon = ContextCompat.getDrawable(ctx, R.drawable.ic_baseline_cancel_24)

            // Si es clicka el chip, l'esborrem
            chip.setOnClickListener {
                filters.remove(chip.text)
                binding.chipsGroup.removeView(it)
                applyFiltersAndRefresh()
            }

            // Afegim el chip a la view, esborrem el text que ha escrit l'usuari i refresh
            filters.add(selection)
            binding.chipsGroup.addView(chip)
            binding.searchBar.setText("")

            applyFiltersAndRefresh()
        }

        return retView
    }

    override fun onResume() {
        super.onResume()

        // Notify some particle can have changed its name
        ParticleRecyclerViewAdapter::class.safeCast(binding.particlesList.adapter)?.notifyParticleUpdated()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData() {
        // En aquest cas, totes les dades de la llista poden haver canviat. Aquí s'hi arriba quan
        // es resetejen totes les dades
        val adapter = ParticleRecyclerViewAdapter::class.safeCast(binding.particlesList.adapter) ?: return
        adapter.particles.clear()
        adapter.particles.addAll(Particles)
        adapter.notifyDataSetChanged()
    }

    /**
     * Aquesta funció serveix per filtrar els elements de la llista que es veuran desprès d'haver
     * sel·leccionat algun filtre. En cas de no haver-hi cap filtre, es mostren tots els elemnts
     */
    @SuppressLint("NotifyDataSetChanged")
    fun applyFiltersAndRefresh() {
        // Important, primer netejem la llista d'elements a mostrar
        particles.clear()

        filters.forEach { filter ->

            // Mirem si el filtre coincideix amb la familia d'alguna partícula
            val byFamily = Particles.filter { it.family.name == filter }
            if (byFamily.isNotEmpty()) {
                particles.addAll(byFamily)
            } else {

                // Mirem si el filtre coincideix amb el nom d'alguna partícula
                val byName = Particles.filter { it.name == filter }
                particles.addAll(byName)
            }
        }

        // En cas de no haver-hi cap "match", les mostrem totes
        if (particles.isEmpty())
            particles.addAll(Particles)

        // IMPORTANTÍSSIM! Hem de notificar a la vista que hem actualitzat els elements de la llista
        binding.particlesList.adapter?.notifyDataSetChanged()
    }
}