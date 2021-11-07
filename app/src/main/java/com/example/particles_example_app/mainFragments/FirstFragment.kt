package com.example.particles_example_app.mainFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.example.particles_example_app.R

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Carrega el layout dins del fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            // Exemple ús de dades del PreferenceManager
            val settings = PreferenceManager.getDefaultSharedPreferences(context)
            val sync = settings.getBoolean("sync", false)

            Log.e("DEBUG", PreferenceManager.getDefaultSharedPreferences(context).all.toString())


            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
}