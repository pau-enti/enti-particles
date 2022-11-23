package com.example.particles.ui.particles2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.particles.databinding.ActivityStandardModelBinding
import com.google.firebase.firestore.FirebaseFirestore

class StandardModelActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    val PARTICLES_COLLECTION = "sm_particles"

    private lateinit var binding: ActivityStandardModelBinding
    private var particles = arrayListOf<SParticle>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStandardModelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = StandardModelAdapter( this)

        firestore = FirebaseFirestore.getInstance()
        firestore.collection(PARTICLES_COLLECTION).get().addOnSuccessListener {
            particles = it.documents.mapNotNull {
                 it.toObject(SParticle::class.java)
             } as ArrayList
            adapter.updateParticles(particles)
        }

        binding.particlesRecyclerView.adapter = adapter
    }

    override fun onPause() {
        super.onPause()

//        SParticles.resetParticles()

        for (p in particles) {
            firestore.collection(PARTICLES_COLLECTION).document(p.name).set(SParticle(p.name, p.family, p.mass, p.charge, p.spin))
        }
    }
}