package com.example.particles.ui

import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.particles.databinding.FragmentDragAndDropBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlin.reflect.safeCast

class DragAndDropFragment : Fragment() {

    private lateinit var binding: FragmentDragAndDropBinding
    private val chipsLabel = listOf(
        "Neutrino", "Chupa-chups", // Not star life
        "Blue supergiant", "Sun-Like Star", "Red Dwarf", "Brown Dwarf", // 1st phase
        "Supernova", "Red Giant", "Blackhole", "Neutron Star", "White Dwarf" // 2nd phase
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDragAndDropBinding.inflate(inflater)

        // Creem els Chips
        val chips = chipsLabel.map {
            Chip(requireContext()).apply {
                text = it

                setOnLongClickListener {
                    val data = ClipData(
                        "description",
                        arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                        ClipData.Item("yes")
                    )

                    val drag = View.DragShadowBuilder(it)
                    it.startDragAndDrop(data, drag, it, 0)

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
     * El parametre view conté l'objecte en el qual hem posat el listener. Aquest s'encarrega
     * d'escoltar els esdeveniments que succeeixen durant el dragging. Event contindrà la informació
     * del que està succeint, així com l'objecte que s'està arrossegant
     */
    val dragListenerMove = View.OnDragListener { view, event ->
        // Obtenim l'objecte que s'està arrossegant de manera segura
        val moving = Chip::class.safeCast(event.localState) ?: return@OnDragListener false

        return@OnDragListener when (event.action) {

            DragEvent.ACTION_DRAG_STARTED -> {
                moving.isVisible = false
                true
            }

            DragEvent.ACTION_DROP -> {
                // From
                ChipGroup::class.safeCast(moving.parent)?.removeView(moving)

                // To
                ChipGroup::class.safeCast(view)?.addView(moving)
                true
            }

            DragEvent.ACTION_DRAG_ENDED -> {
                moving.isVisible = true
                true
            }

            else -> false
        }
    }

    val dragListenerRemove = View.OnDragListener { view, event ->
        val moving = Chip::class.safeCast(event.localState) ?: return@OnDragListener false

        return@OnDragListener when (event.action) {

            // 1. Comença el drag: Hem de retornar ture per continuar el drag. False per detenir-lo
            DragEvent.ACTION_DRAG_STARTED -> {
                moving.isVisible = false
                binding.recicleBin.alpha = 1f // visible
                true
            }

            DragEvent.ACTION_DRAG_ENTERED -> true // Ens notifica que hem entrat dins la vista "view"
            DragEvent.ACTION_DRAG_LOCATION -> true // Ens notifica la posició del objecte dins la "view"
            DragEvent.ACTION_DRAG_EXITED -> true // Ens notifica que hem sortit de la vista

            // 2. Ens notifica que hem deixat anar l'element sobre la vista
            DragEvent.ACTION_DROP -> {
                ChipGroup::class.safeCast(moving.parent)?.removeView(moving)
                true
            }

            // 3. Quan acabem el drag
            DragEvent.ACTION_DRAG_ENDED -> {
                moving.isVisible = true
                binding.recicleBin.alpha = 0f // invisible
                true
            }

            else -> false
        }
    }
}