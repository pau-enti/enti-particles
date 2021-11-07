package com.example.particles_example_app.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.particles_example_app.ui.StandardModelFragment
import com.example.particles_example_app.ui.particles_list.ParticlesFragment
import java.lang.IllegalStateException


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val TAB_TITLES = arrayOf(
        "STANDARD MODEL",
        "PARTICLES"
    )

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> StandardModelFragment()
            1 -> ParticlesFragment()
            else -> throw IllegalStateException("There's only 2 tabs")
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