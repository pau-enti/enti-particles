package com.example.particles.ui.dragging

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.particles.R

data class Star(
    val name: String,
    val lifeStatus: LifeStatus
) {
    enum class LifeStatus {
        ALIVE, DEATH, NOT_A_STAR;
    }

    fun getIcon(context: Context): Drawable? {
        val ic = when (lifeStatus) {
            LifeStatus.ALIVE -> R.drawable.ic_star_life
            LifeStatus.DEATH -> R.drawable.ic_star_death
            else -> R.drawable.ic_star_not
        }
        return ContextCompat.getDrawable(context, ic)
    }
}