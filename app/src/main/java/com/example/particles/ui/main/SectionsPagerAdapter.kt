package com.example.particles.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.particles.ui.ParticlesWithSimpleAdapterFragment
import com.example.particles.ui.StandardModelFragment
import com.example.particles.ui.particles_list.ParticlesFragment
import java.lang.IllegalStateException


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val TAB_TITLES = arrayOf(
        "STANDARD MODEL",
        "PARTICLES",
        "PARTICLES 2"
    )

    // Aquesta funció indica el fragment que s'ha de posar
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> StandardModelFragment()
            1 -> ParticlesFragment()
            2 -> ParticlesWithSimpleAdapterFragment()
            else -> throw IllegalStateException("There's only 3 tabs")
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return TAB_TITLES.size
    }
}