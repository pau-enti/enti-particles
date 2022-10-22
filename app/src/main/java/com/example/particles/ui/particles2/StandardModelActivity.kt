package com.example.particles.ui.particles2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.particles.databinding.ActivityStandardModelBinding

class StandardModelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStandardModelBinding
    private val particles = listOf("Quark Up",
        "Quark Charm",
        "Quark Top",
        "Quark Down",
        "Quark Strange",
        "Quark Bottom",
        "Electron",
        "Muon",
        "Tau",
        "Electron neutrino",
        "Muon neutrino",
        "Tau neutrino",
        "Gluon",
        "Photon",
        "Z boson",
        "W boson",
        "Higgs")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStandardModelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = StandardModelAdapter(particles)
        binding.particlesRecyclerView.adapter = adapter
    }
}