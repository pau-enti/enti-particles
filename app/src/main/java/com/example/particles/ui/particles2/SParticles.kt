package com.example.particles.ui.particles2


object SParticles : ArrayList<SParticle>() {
    fun resetParticles() {
        SParticles.clear()

        SParticles.add(SParticle("Quark Up", SParticle.Family.QUARK, 2200.0, "2/3", "1/2"))
        SParticles.add(SParticle("Quark Charm", SParticle.Family.QUARK, 1280.0, "2/3", "1/2"))
        SParticles.add(SParticle("Quark Top", SParticle.Family.QUARK, 173100.0, "2/3", "1/2"))

        SParticles.add(SParticle("Quark Down", SParticle.Family.QUARK, 4.7, "-1/3", "1/2"))
        SParticles.add(SParticle("Quark Strange", SParticle.Family.QUARK, 96.0, "-1/3", "1/2"))
        SParticles.add(SParticle("Quark Bottom", SParticle.Family.QUARK, 4.18, "-1/3", "1/2"))

        SParticles.add(SParticle("Electron", SParticle.Family.LEPTON, 0.511, "-1", "1/2"))
        SParticles.add(SParticle("Muon", SParticle.Family.LEPTON, 105.66, "-1", "1/2"))
        SParticles.add(SParticle("Tau", SParticle.Family.LEPTON, 1776.8, "-1", "1/2"))

        SParticles.add(SParticle("Electron neutrino", SParticle.Family.LEPTON, 1.0, "0", "1/2"))
        SParticles.add(SParticle("Muon neutrino", SParticle.Family.LEPTON, 0.17, "0", "1/2"))
        SParticles.add(SParticle("Tau neutrino", SParticle.Family.LEPTON, 18.2, "0", "1/2"))

        SParticles.add(SParticle("Gluon", SParticle.Family.GAUGE_BOSON, 0.0, "0", "1"))
        SParticles.add(SParticle("Photon", SParticle.Family.GAUGE_BOSON, 0.0, "0", "1"))
        SParticles.add(SParticle("Z boson", SParticle.Family.GAUGE_BOSON, 91190.0, "0", "1"))
        SParticles.add(SParticle("W boson", SParticle.Family.GAUGE_BOSON, 80930.0, "±1", "1"))

        SParticles.add(SParticle("Higgs", SParticle.Family.SCALAR_BOSON, 124970.0, "0", "0"))
    }
}