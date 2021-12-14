package com.example.particles_example_app.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.particles_example_app.LoginActivity
import com.example.particles_example_app.R
import com.example.particles_example_app.SettingsActivity
import com.example.particles_example_app.databinding.ActivityMainBinding
import com.example.particles_example_app.utils.toast
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

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
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Do you want to know something?", Snackbar.LENGTH_LONG)
                .setAction("YES") {
                    toast("Maybe we live in a simulation")
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