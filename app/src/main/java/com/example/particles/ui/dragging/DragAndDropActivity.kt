package com.example.particles.ui.dragging

import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.particles.R
import com.example.particles.databinding.ActivityDragAndDropBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlin.reflect.safeCast

class DragAndDropActivity : AppCompatActivity() {

    private val GAME_TIME: Long = 30_000L
    private lateinit var binding: ActivityDragAndDropBinding

    private val aliveGroup = arrayListOf<Star>()
    private val deathGroup = arrayListOf<Star>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDragAndDropBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        // Set each element in one random position
        Stars.getAllStars().forEach { star ->
            val chip = Chip(this)
            chip.text = star.name
            chip.chipIcon = star.getIcon(this)

            // Col·loquem el chip en un dels dos grups aleatoriament
            if (listOf(true, false).random()) {
                aliveGroup.add(star)
                binding.aliveChipsGroup.addView(chip)
            } else {
                deathGroup.add(star)
                binding.deathChipsGroup.addView(chip)
            }

            // Implementem el moviment del chip, que comenci el dragging
            chip.setOnLongClickListener {
                val shadow = View.DragShadowBuilder(it)
                it.startDragAndDrop(null, shadow, star to chip, 0)
            }
        }

        binding.aliveChipsGroup.setOnDragListener(dragListenerMove)
        binding.deathChipsGroup.setOnDragListener(dragListenerMove)

        startBlackholeAnimations()
    }

    private val dragListenerMove = View.OnDragListener { destinationView, draggingData ->

        // Obtenim les dades de l'objecte que s'arrossega
        val data = draggingData.localState
        if (data !is Pair<*, *>)
            return@OnDragListener false

        val chip = data.second
        val star = data.first

        if (chip !is Chip || star !is Star)
            return@OnDragListener false

        when (draggingData.action) {
            // 1. Comença el drag: Hem de retornar true per continuar el drag. False per detenir-lo
            DragEvent.ACTION_DRAG_STARTED -> {
                chip.isVisible = false
            }

            // 2. Ens notifica que hem deixat anar l'element sobre la vista
            DragEvent.ACTION_DROP -> {

                // Remove form old view, insert to destination view
                ChipGroup::class.safeCast(chip.parent)?.removeView(chip)
                ChipGroup::class.safeCast(destinationView)?.addView(chip)

                // Update data lists
                if (destinationView.id == binding.aliveChipsGroup.id) {
                    aliveGroup.add(star)
                    deathGroup.remove(star)
                } else {
                    deathGroup.add(star)
                    aliveGroup.remove(star)
                }

                checkVictory()
            }

            // 3. Quan acabem el drag
            DragEvent.ACTION_DRAG_ENDED -> {
                chip.isVisible = true
            }
        }

        return@OnDragListener true
    }

    /**
     * GAME CONTROLLER
     */

    private fun loseGame() {
        binding.loseMessage.isVisible = true

        // Desactivem el drag
        binding.aliveChipsGroup.setOnDragListener(null)
        binding.deathChipsGroup.setOnDragListener(null)

        // BH creix
        binding.blackhole.animate().scaleX(10f).scaleY(10f).apply {
            duration = 1_000
        }
    }

    private fun checkVictory() {
        if (aliveGroup.all { it.lifeStatus == Star.LifeStatus.ALIVE } &&
            deathGroup.all { it.lifeStatus == Star.LifeStatus.DEATH }) {

            Toast.makeText(this, getString(R.string.won_message), Toast.LENGTH_SHORT).show()

            // Tornem el BH a la seva mida original
            binding.blackhole.animate().scaleX(0f).scaleY(0f).apply {
                duration = 1_000
            }

            // Desactivem el drag
            binding.aliveChipsGroup.setOnDragListener(null)
            binding.deathChipsGroup.setOnDragListener(null)
        }
    }

    private fun startBlackholeAnimations() {
        // Creix
        binding.blackhole.animate().scaleX(5f).scaleY(5f).apply {
            duration = GAME_TIME
            withEndAction {
                loseGame()
            }
        }

        // Gira
        binding.blackhole.startAnimation(
            RotateAnimation(
                0f,
                360f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            ).apply {
                repeatCount = Animation.INFINITE
                duration = 3_000L
            })
    }
}