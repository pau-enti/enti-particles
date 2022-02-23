package com.example.particles.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.particles.LoginActivity
import com.example.particles.R
import com.example.particles.SettingsActivity
import com.example.particles.data.Particles
import com.example.particles.databinding.ActivityMainBinding
import com.example.particles.utils.toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import java.io.FileInputStream
import java.io.IOException
import java.io.ObjectInputStream


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val USER_EXTRA = "USERNAME_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Afegim el layout del main
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Creem l'adapter pel ViewPager i el coloquem on toca
        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        binding.viewPager.adapter = sectionsPagerAdapter

        // Amaga el floating action button en funcio de la pagina que es mostra
        binding.viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.fab.hide()
                    else -> binding.fab.show()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        // Vinculem el ViewPager amb el TabLayout (gestor de pestanyes)
        binding.tabs.setupWithViewPager(binding.viewPager)

        // Afegim una acció al botó flotant
        binding.fab.setOnClickListener { view ->
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

        initData()
    }

    private fun initData() {
        if (Particles.isEmpty()) {
            val data = try {
                openFileInput("particlesInternalData.dat").use { io ->
                    ObjectInputStream(io).use {
                        // Si tot va bé, l'objecte llegit estarà dins de "data"
                        it.readObject() as Particles
                    }
                }
            } catch (e: IOException) {
                null // En cas d'error, "data" valdrà null
            }

            if (data?.isNotEmpty() == true)
                Particles.addAll(data)
            else
                Particles.resetParticles()
        }
    }


    private fun logout() {
        FirebaseAuth.getInstance().signOut()

        // Then, finish this activity and go to the LoginActivity
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}