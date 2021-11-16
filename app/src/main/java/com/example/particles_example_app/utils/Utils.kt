package com.example.particles_example_app.utils

import android.content.Context
import android.widget.Toast

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Int.applyTransparency(): Int {
    // Primer fa una màscara amb l'AND per suprimir la transparència que té el color
    // El valor de alpha es troba als 2 primers bytes
    // Seguidament, amb l'OR li sumem el valor de la transparència que volem (50%)
    return (this and 0x00FFFFFF) or 0x32000000
}