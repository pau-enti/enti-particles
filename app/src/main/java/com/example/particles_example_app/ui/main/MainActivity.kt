package com.example.particles_example_app.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.particles_example_app.R
import com.example.particles_example_app.utils.toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    companion object {
        const val USERNAME_EXTRA = "USERNAME_EXTRA"
        const val PASSWORD_EXTRA = "PASSWORD_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Afegim el layout del main
        setContentView(R.layout.activity_main)

        // Creem l'adapter pel ViewPager i el coloquem on toca
        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        // Vinculem el ViewPager amb el TabLayout (gestor de pestanyes)
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        // Afegim una acció al botó flotant
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Do you want to know something?", Snackbar.LENGTH_LONG)
                .setAction("YES") {
                    toast("Maybe we live in a simulation")
                }.show()
        }

        // Agafem els paràmetres que ens porta l'extra
        intent.extras?.get(USERNAME_EXTRA)?.let {
            toast("Welcome $it") // Si l'usuari no és null, fem el toast
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}