package com.example.particles.ui.particles2

import android.app.AlertDialog
import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.particles.databinding.ItemListParticleBinding


class StandardModelAdapter(val context: Context) :
    RecyclerView.Adapter<StandardModelAdapter.ParticleViewHolder>() {

    var particles: ArrayList<SParticle> = arrayListOf()

    inner class ParticleViewHolder(binding: ItemListParticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val particleName = binding.particleName
        val particleImage = binding.particleImage
        val particleLayout = binding.particleLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, xxx: Int): ParticleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemListParticleBinding.inflate(layoutInflater, parent, false)
        return ParticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParticleViewHolder, position: Int) {
        holder.particleName.text = particles[position].name

        holder.particleLayout.setOnLongClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Particle name")
            val input = EditText(context)

            input.inputType = InputType.TYPE_CLASS_TEXT /*or InputType.TYPE_TEXT_VARIATION_PASSWORD*/
            builder.setView(input)

            builder.setPositiveButton("OK") { dialog, which ->
                particles[position].name = input.text.toString()
                notifyItemChanged(position)
            }
            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }

            builder.show()
            true
        }
    }

    fun updateParticles(particles: ArrayList<SParticle>) {
        this.particles = particles
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return particles.size
    }
}