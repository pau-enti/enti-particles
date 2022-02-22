package com.example.particles.ui

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.graphics.drawable.Drawable
import android.os.*
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.particles.R
import com.example.particles.databinding.FragmentDragAndDropBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlin.reflect.safeCast


class DragAndDropFragment : Fragment() {

    private lateinit var binding: FragmentDragAndDropBinding
    private val chipsLabel = listOf(
        "Blue supergiant", "Sun-Like Star", "Red Dwarf", "Brown Dwarf", "Red Giant",// 1st phase
        "Supernova", "Blackhole", "Neutron Star", "White Dwarf", // 2nd phase
        "Neutrino", "Chupa-chups", // Not star life
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDragAndDropBinding.inflate(inflater)

        // Creem els Chips
        val chips = chipsLabel.mapIndexed { idx, label ->
            Chip(requireContext()).apply {
                text = label
                chipIcon = getIcon(idx)

                setOnLongClickListener {
                    val drag = View.DragShadowBuilder(it)
                    it.startDragAndDrop(null, drag, it, 0)

                    // Retornem true per indicar que el click "ja s'ha consumit" i no cal fer res més
                    true
                }
            }
        }

        // Afegim els Chips al primer grup
        chips.forEach {
            binding.chipGroup1.addView(it)
        }

        binding.chipGroup1.setOnDragListener(dragListenerMove)
        binding.chipGroup2.setOnDragListener(dragListenerMove)

        binding.recicleBin.alpha = 0f // invisible
        binding.recicleBin.setOnDragListener(dragListenerRemove)

        return binding.root
    }

    /**
     * El parametre "view" conté l'objecte en el qual hem posat el listener. Aquest s'encarrega
     * d'escoltar els esdeveniments que succeeixen durant el dragging.
     *
     * El paràmetre "event" contindrà la informació del que està succeint, així com l'objecte que
     * s'està arrossegant
     */
    private val dragListenerMove = View.OnDragListener { view, event ->

        // Obtenim l'objecte que s'està arrossegant de manera segura
        val moving = Chip::class.safeCast(event.localState) ?: return@OnDragListener false

        return@OnDragListener when (event.action) {

            // 1. Comença el drag: Hem de retornar ture per continuar el drag. False per detenir-lo
            DragEvent.ACTION_DRAG_STARTED -> {
                moving.isVisible = false
                true
            }

            // 2. Ens notifica que hem deixat anar l'element sobre la vista
            DragEvent.ACTION_DROP -> {
                // From
                ChipGroup::class.safeCast(moving.parent)?.removeView(moving)

                // To
                ChipGroup::class.safeCast(view)?.addView(moving)
                true
            }

            // 3. Quan acabem el drag
            DragEvent.ACTION_DRAG_ENDED -> {
                moving.isVisible = true
                true
            }

            else -> false
        }
    }

    private val dragListenerRemove = View.OnDragListener { _, event ->
        val moving = Chip::class.safeCast(event.localState) ?: return@OnDragListener false

        return@OnDragListener when (event.action) {

            DragEvent.ACTION_DRAG_STARTED -> {
                moving.isVisible = false
                binding.recicleBin.animate().alpha(1f) // visible
                true
            }

            // Ens notifica que hem entrat dins la vista "view"
            DragEvent.ACTION_DRAG_ENTERED -> {
                vibrate()
                // Fem gran la icona per suprimir l'objecte (multiplica per 2 la mida)
                binding.recicleBin.animate().scaleX(1.6f).scaleY(1.6f)
                true
            }

            DragEvent.ACTION_DRAG_LOCATION -> true // Ens notifica la posició del objecte dins la "view"

            // Ens notifica que l'arrossagament ha sortit de la vista
            DragEvent.ACTION_DRAG_EXITED -> {
                // Tornem la icona a la mida original quan arrosseguem fora
                binding.recicleBin.animate().scaleX(1f).scaleY(1f)
                true
            }

            DragEvent.ACTION_DROP -> {
                // Si es deixa anar dins la icona, se suprimeix l'objecte
                ChipGroup::class.safeCast(moving.parent)?.removeView(moving)
                true
            }

            DragEvent.ACTION_DRAG_ENDED -> {
                moving.isVisible = true

                // Animació de desapareixre: Es fa petit mentres es fa invisible
                binding.recicleBin.animate().scaleX(0f).scaleY(0f).withEndAction {
                    binding.recicleBin.alpha = 0f // Invisible

                    // Un cop finalitzada l'animació, deixem l'objecte a la mida original
                    binding.recicleBin.scaleX = 1f
                    binding.recicleBin.scaleY = 1f
                }
                true
            }

            else -> false
        }
    }

    // Hardcorejar les posicions d'una llista és molt mala opció!
    private fun getIcon(idx: Int): Drawable? {
        val ic = when (idx) {
            in 0..4 -> R.drawable.ic_star_life
            in 5..8 -> R.drawable.ic_star_death
            else -> R.drawable.ic_star_not
        }
        return ContextCompat.getDrawable(requireContext(), ic)
    }

    private fun vibrate() {
        val duration = 250L // ms

        // Hem d'obtenir l'objecte per vibrar de manera diferent en funció de la versió d'Android del dispositiu...
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                context?.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator.vibrate(
                VibrationEffect.createOneShot(
                    duration,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            // En versions inferiors, es fan servir classes i funcions depecated
            val vib = context?.getSystemService(VIBRATOR_SERVICE) as Vibrator
            vib.vibrate(duration)
        }
    }

}