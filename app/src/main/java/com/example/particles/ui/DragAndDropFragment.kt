package com.example.particles.ui

import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.particles.databinding.FragmentDragAndDropBinding
import com.example.particles.utils.toast
import com.google.android.material.chip.Chip

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
                    val data = ClipData("description", arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), ClipData.Item("yes"))

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

        binding.chipGroup1.setOnDragListener(dragListener)
        binding.chipGroup2.setOnDragListener(dragListener)





        return binding.root
    }

    val dragListener = View.OnDragListener { view, event ->

        return@OnDragListener when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> true // Hem de retornar ture per continuar el drag
            DragEvent.ACTION_DRAG_ENTERED -> true // Ens notifica que hem entrat dins la vista "view"
            DragEvent.ACTION_DRAG_LOCATION -> true // Ens notifica la posició del objecte dins la "view"
            DragEvent.ACTION_DRAG_EXITED -> true // Ens notifica que hem sortit de la vista
            DragEvent.ACTION_DROP -> {
                // Ens notifica que hem sortit de la vista
                context?.toast("Inside $view")

                true
            }
            DragEvent.ACTION_DRAG_ENDED -> true
            else -> false
        }



    }
}