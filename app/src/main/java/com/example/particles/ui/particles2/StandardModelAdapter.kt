package com.example.particles.ui.particles2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.particles.databinding.ItemListParticleBinding

class StandardModelAdapter(val particles: List<String>): RecyclerView.Adapter<StandardModelAdapter.ParticleViewHolder>() {

    inner class ParticleViewHolder(binding: ItemListParticleBinding): RecyclerView.ViewHolder(binding.root) {
        val particleName = binding.particleName
        val particleImage = binding.particleImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, xxx: Int): ParticleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemListParticleBinding.inflate(layoutInflater)
        return ParticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParticleViewHolder, position: Int) {
        holder.particleName.text = particles[position]
    }

    override fun getItemCount(): Int {
        return particles.size
    }
}