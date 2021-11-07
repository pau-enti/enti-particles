package com.example.particles_example_app.data

import kotlin.random.Random

/**
 * Represents a particle of the Standard Model
 *
 * https://en.wikipedia.org/wiki/Standard_Model
 */
data class Particle(
    val name: String,
    val family: Family,
    val mass: Double,
    val charge: Double,
    val spin: Double
) {

    // Second constructor with optional parameters
    constructor(name: String, family: Family) : this(
        name,
        family,
        getRandomValue(),
        getRandomValue(),
        getRandomValue()
    )

    enum class Family {
        QUARK, LEPTON, GAUGE_BOSON, SCALAR_BOSON
    }

    companion object {
        private fun getRandomValue(): Double = Random.nextInt(0, 999) / 1000.0
    }
}