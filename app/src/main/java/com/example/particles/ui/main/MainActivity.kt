package com.example.particles.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.particles.LoginActivity
import com.example.particles.R
import com.example.particles.SettingsActivity
import com.example.particles.data.Particle
import com.example.particles.data.Particles
import com.example.particles.databinding.ActivityMainBinding
import com.example.particles.utils.toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import java.io.IOException
import java.io.ObjectInputStream
import kotlin.reflect.safeCast


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // This bool is used when enters to settings that can modify particles view
    private var needsRefresh = false

    companion object {
        const val USER_EXTRA = "USERNAME_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Afegim el layout del main
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Creem l'adapter pel ViewPager i el coloquem on toca
        val sectionsPagerAdapter = PageViewAdapter(supportFragmentManager)
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
            Snackbar.make(view, "Do you want to reset all data?", Snackbar.LENGTH_LONG)
                .setAction("YES") {
                    // Carregem les dades de nou
                    Particles.resetParticles()

                    // Notifiquem a l'adapter PageViewAdapter que alguna de les pàgines ha canviat
                    // el contingut. En aquest cas, la de la info de partícules
                    PageViewAdapter::class.safeCast(binding.viewPager.adapter)?.refreshData()

                    toast("You left the universe as it was")
                }.show()
        }

        // Gestionem el menú
        binding.menuMainActivity.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_logout -> logout()
                R.id.action_settings -> startActivity(
                    Intent(this, SettingsActivity::class.java)
                ).also { needsRefresh = true }
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
                openFileInput(Particles.PARTICLES_FILENAME).use { io ->
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

    override fun onResume() {
        super.onResume()

        if (needsRefresh) {
            needsRefresh = false

            val prefs = PreferenceManager.getDefaultSharedPreferences(this)
            val newParticles = prefs.getStringSet("extraParticles", HashSet())

            Particles.resetParticles()

            newParticles?.forEach { p ->
                Particles.add(Particle(p, Particle.Family.ANTIPARTICLE))
            }

            Particles.apply {
                // Before the clear, we need to save the new list
                val aux = Particles.distinctBy { it.name }
                clear()
                addAll(aux)
            }

            PageViewAdapter::class.safeCast(binding.viewPager.adapter)?.refreshData()
        }
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()

        // Then, finish this activity and go to the LoginActivity
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}