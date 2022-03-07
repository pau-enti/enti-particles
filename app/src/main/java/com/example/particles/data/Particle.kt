package com.example.particles.data

import com.example.particles.R
import java.io.Serializable
import kotlin.random.Random

/**
 * Represents a particle of the Standard Model
 *
 * https://en.wikipedia.org/wiki/Standard_Model
 */
data class Particle(
    var name: String,
    var family: Family,
    var mass: Double, // MeV / c^2
    var charge: String,
    var spin: String

// TODO: La interface Parceable és més eficient per Android, però no tan trivial
): Serializable {

    // Second constructor with optional parameters
    constructor(name: String, family: Family) : this(
        name,
        family,
        getRandomDouble(),
        getRandomInt(),
        getRandomInt()
    )

    enum class Family {
        QUARK, LEPTON, GAUGE_BOSON, SCALAR_BOSON, ANTIPARTICLE;

        fun color(): Int = when (this) {
            QUARK -> R.color.quarks
            LEPTON -> R.color.leptons
            GAUGE_BOSON -> R.color.gauge_bosons
            SCALAR_BOSON -> R.color.higgs
            ANTIPARTICLE -> R.color.antiparticle
        }
    }

    companion object {
        private fun getRandomDouble(): Double = Random.nextInt(0, 999) / 1000.0
        private fun getRandomInt(): String = Random.nextInt(0, 3).toString()
    }
}