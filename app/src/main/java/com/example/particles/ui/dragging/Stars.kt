package com.example.particles.ui.dragging

import com.example.particles.ui.dragging.Star.LifeStatus.*

object Stars {
    fun checkStatusOf(star: String): Star.LifeStatus {
        return if (aliveStars.any { it.name == star })
            ALIVE
        else if (deathStars.any { it.name == star })
            DEATH
        else NOT_A_STAR
    }

    fun getAllStars() = aliveStars + deathStars + noStars

    private val aliveStars = listOf(
        Star("Blue supergiant", ALIVE),
        Star("Sun-Like Star", ALIVE),
        Star("Red Dwarf", ALIVE),
        Star("Brown Dwarf", ALIVE),
        Star("Red Giant", ALIVE),
        Star("The Sun", ALIVE)
    )

    private val deathStars = listOf(
        Star("Supernova", DEATH),
        Star("Blackhole", DEATH),
        Star("Center of The Milky Way", DEATH),
        Star("Neutron Star", DEATH),
        Star("Jupiter", DEATH),
        Star("White Dwarf", DEATH)
    )

    private val noStars = listOf<Star>(
        // Star("Neutrino", NOT_A_STAR),
        //Star("Chupa-chups", NOT_A_STAR)
    )
}