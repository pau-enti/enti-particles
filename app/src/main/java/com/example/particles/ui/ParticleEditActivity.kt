package com.example.particles.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.particles.data.Particle
import com.example.particles.data.Particles
import com.example.particles.databinding.ActivityParticleEditBinding
import java.io.FileOutputStream
import java.io.ObjectOutputStream

class ParticleEditActivity : AppCompatActivity() {

    companion object {
        const val INTENT_EXTRA_PARTICLE_ID = "INTENT_EXTRA_PARTICLE_ID"
    }

    private lateinit var binding: ActivityParticleEditBinding
    private var particle: Particle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityParticleEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val particleId = intent.extras?.getInt(INTENT_EXTRA_PARTICLE_ID)
            ?: return // leave it empty if no extra passed
        particle = Particles[particleId]
        fillParticlesInfo()
    }

    override fun onPause() {
        super.onPause()

        // Update class with user input data
        particle?.name = binding.particleNameInput.text.toString()
        particle?.mass = binding.particleMassInput.text.toString().toDoubleOrNull() ?: 0.0
        particle?.charge = binding.particleChargeInput.text.toString()
        particle?.spin = binding.particleSpinInput.text.toString()

        openFileOutput(Particles.PARTICLES_FILENAME, Context.MODE_PRIVATE).use { io ->
            ObjectOutputStream(io).use {
                // Save the object on the file
                it.writeObject(Particles)
            }
        }
    }

    private fun fillParticlesInfo() {
        particle?.let { p ->
            binding.particleNameInput.setText(p.name)
            binding.particleMassInput.setText(p.mass.toString())
            binding.particleChargeInput.setText(p.charge)
            binding.particleSpinInput.setText(p.spin)
        }
    }

    fun a() {
        // Manera 1
        val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = sp.edit()
//        editor.putStringSet()

        editor.apply()


        // Manera 2
        val fos: FileOutputStream = openFileOutput("fileName", Context.MODE_PRIVATE)
        val os = ObjectOutputStream(fos)
        os.writeObject(this)
        os.close()
        fos.close()
    }


}

