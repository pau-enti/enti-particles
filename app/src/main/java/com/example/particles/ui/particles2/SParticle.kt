package com.example.particles.ui.particles2

data class SParticle(
    var name: String="",
    val family: Family=Family.QUARK,
    val mass: Double=0.0,
    val charge: String="",
    val spin: String="",
): java.io.Serializable {
    enum class Family {
        QUARK, LEPTON, GAUGE_BOSON, SCALAR_BOSON;
    }
}


