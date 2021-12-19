package com.example.particles.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.particles.LoginActivity
import com.example.particles.R
import com.example.particles.SettingsActivity
import com.example.particles.databinding.ActivityMainBinding
import com.example.particles.utils.toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val USER_EXTRA = "USERNAME_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Afegim el layout del main
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Creem l'adapter pel ViewPager i el coloquem on toca
        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        binding.viewPager.adapter = sectionsPagerAdapter

        // Vinculem el ViewPager amb el TabLayout (gestor de pestanyes)
        binding.tabs.setupWithViewPager(binding.viewPager)

        // Afegim una acció al botó flotant
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Do you want to know something?", Snackbar.LENGTH_LONG)
                .setAction("YES") {
                    applicationContext?.toast("Maybe we live in a simulation")
                }.show()
        }

        // Gestionem el menú
        binding.menuMainActivity.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_settings -> startActivity(Intent(this, SettingsActivity::class.java))
                R.id.action_logout -> logout()
                else -> return@setOnMenuItemClickListener false
            }
            true
        }


        // Agafem els paràmetres que ens porta l'extra
        intent.extras?.get(USER_EXTRA)?.let {
            toast("Welcome $it") // Si l'usuari no és null, fem el toast
        }
    }


    fun logout() {
        // TODO: Make the necessary things to logout the user

        // Then, finish this activity and go to the LoginActivity
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}