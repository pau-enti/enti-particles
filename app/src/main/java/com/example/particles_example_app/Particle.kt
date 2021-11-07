package com.example.particles_example_app

/**
 * Represents a particle of the Standard Model
 *
 * https://en.wikipedia.org/wiki/Standard_Model
 */
data class Particle(
    val name: String,
    val mass: Double,
    val charge: Double,
    val spin: Double
) {


}