package com.example.particles.data

import com.example.particles.data.Particle.Family.*

/**
 * Collection of particles of the Standard Model
 */
object Particles : ArrayList<Particle>() {

    const val PARTICLES_FILENAME = "particlesInternalData.dat"

    fun resetParticles() {
        clear()

        add(Particle("Quark Up", QUARK, 2200.0, "2/3", "1/2"))
        add(Particle("Quark Charm", QUARK, 1280.0, "2/3", "1/2"))
        add(Particle("Quark Top", QUARK, 173100.0, "2/3", "1/2"))

        add(Particle("Quark Down", QUARK, 4.7, "-1/3", "1/2"))
        add(Particle("Quark Strange", QUARK, 96.0, "-1/3", "1/2"))
        add(Particle("Quark Bottom", QUARK, 4.18, "-1/3", "1/2"))

        add(Particle("Electron", LEPTON, 0.511, "-1", "1/2"))
        add(Particle("Muon", LEPTON, 105.66, "-1", "1/2"))
        add(Particle("Tau", LEPTON, 1776.8, "-1", "1/2"))

        add(Particle("Electron neutrino", LEPTON, 1.0, "0", "1/2"))
        add(Particle("Muon neutrino", LEPTON, 0.17, "0", "1/2"))
        add(Particle("Tau neutrino", LEPTON, 18.2, "0", "1/2"))

        add(Particle("Gluon", GAUGE_BOSON, 0.0, "0", "1"))
        add(Particle("Photon", GAUGE_BOSON, 0.0, "0", "1"))
        add(Particle("Z boson", GAUGE_BOSON, 91190.0, "0", "1"))
        add(Particle("W boson", GAUGE_BOSON, 80930.0, "±1", "1"))

        add(Particle("Higgs", SCALAR_BOSON, 124970.0, "0", "0"))
    }
}