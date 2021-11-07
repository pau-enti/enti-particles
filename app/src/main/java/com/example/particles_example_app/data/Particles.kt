package com.example.particles_example_app.data

import com.example.particles_example_app.data.Particle.Family.*

object Particles: ArrayList<Particle>() {

    init {
        add(Particle("Up", QUARK))
        add(Particle("Charm", QUARK))
        add(Particle("Top", QUARK))

        add(Particle("Down", QUARK))
        add(Particle("Strange", QUARK))
        add(Particle("Bottom", QUARK))

        add(Particle("Electron", LEPTON))
        add(Particle("Muon", LEPTON))
        add(Particle("Tau", LEPTON))

        add(Particle("Electron neutrino", LEPTON))
        add(Particle("Muon neutrino", LEPTON))
        add(Particle("Tau neutrino", LEPTON))

        add(Particle("Gluon", GAUGE_BOSON))
        add(Particle("Photon", GAUGE_BOSON))
        add(Particle("Z boson", GAUGE_BOSON))
        add(Particle("W boson", GAUGE_BOSON))

        add(Particle("Higgs", SCALAR_BOSON))
    }
}