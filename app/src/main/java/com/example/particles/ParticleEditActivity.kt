package com.example.particles

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.example.particles.data.Particles
import com.example.particles.databinding.ActivityParticleEditBinding
import java.io.FileOutputStream
import java.io.ObjectOutputStream

class ParticleEditActivity : AppCompatActivity() {

    companion object {
        const val INTENT_EXTRA_PARTICLE_ID = "INTENT_EXTRA_PARTICLE_ID"
    }

    private lateinit var binding: ActivityParticleEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityParticleEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val particleId = intent.extras?.getInt(INTENT_EXTRA_PARTICLE_ID) ?: return // leave it empty if no extra passed
        fillParticlesInfo(particleId)
    }

    override fun onPause() {
        super.onPause()

        val fos: FileOutputStream = openFileOutput("particlesInternalData.dat", Context.MODE_PRIVATE)
        val os = ObjectOutputStream(fos)
        os.writeObject(Particles)

        os.close()
        fos.close()
    }

    private fun fillParticlesInfo(particleId: Int) {
        val particle = Particles[particleId]

        binding.particleNameInput.setText(particle.name)
        binding.particleMassInput.setText(particle.mass.toString())
        binding.particleChargeInput.setText(particle.charge)
        binding.particleSpinInput.setText(particle.spin)

        val list = particle.companionParticles.fold("") { acc, s ->
            "$acc*$s\n"
        }

        binding.companionParticlesList.text = list
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

