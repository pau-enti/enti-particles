package com.example.particles.data

import android.os.Parcelable
import com.example.particles.R
import java.io.Serializable
import kotlin.random.Random

/**
 * Represents a particle of the Standard Model
 *
 * https://en.wikipedia.org/wiki/Standard_Model
 */
data class Particle(
    val name: String,
    val family: Family,
    val mass: Double, // MeV / c^2
    val charge: String,
    val spin: String,
    val companionParticles: ArrayList<String> = ArrayList()

// TODO: La interface Parceable és més eficient per Android, però no tan trivial
): Serializable {

    // Second constructor with optional parameters
//    constructor(name: String, family: Family) : this(
//        name,
//        family,
//        getRandomValue(),
//        getRandomValue(),
//        getRandomValue()
//    )

    enum class Family {
        QUARK, LEPTON, GAUGE_BOSON, SCALAR_BOSON;

        fun color(): Int = when (this) {
            QUARK -> R.color.quarks
            LEPTON -> R.color.leptons
            GAUGE_BOSON -> R.color.gauge_bosons
            SCALAR_BOSON -> R.color.higgs
        }
    }

//    companion object {
//        private fun getRandomValue(): Double = Random.nextInt(0, 999) / 1000.0
//    }
}