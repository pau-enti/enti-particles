package com.example.particles.ui.fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.particles.R
import com.example.particles.databinding.ActivityBottomNavegationBinding

class BottomNavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavegationBinding

    val star = StarFragment()
    val death = QuotesFragment()
    val not = NotFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomNavegationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setCurrentFragment(star, "star")

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_star_life -> setCurrentFragment(star, "star")
                R.id.menu_star_death -> setCurrentFragment(death, "death")
                R.id.menu_star_not -> setCurrentFragment(not, "not")
            }
            true
        }


    }

    private fun setCurrentFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction().apply {
            supportFragmentManager.fragments.forEach {
                hide(it)
            }

            if (fragment.isAdded)
                show(fragment)
             else
                add(binding.flFragment.id, fragment)


            commit()
        }
    }
}