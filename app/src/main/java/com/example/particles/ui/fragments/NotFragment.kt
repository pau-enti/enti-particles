package com.example.particles.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.particles.databinding.FragmentNotBinding
import com.example.particles.databinding.FragmentStarBinding

class NotFragment: Fragment() {

    private lateinit var binding: FragmentNotBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNotBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun getResponse(): String? {
        return if (::binding.isInitialized)
            binding.editTextTextPersonName.text.toString()
        else
            null
    }
}