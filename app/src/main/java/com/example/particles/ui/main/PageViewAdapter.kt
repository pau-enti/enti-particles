package com.example.particles.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.particles.ui.DragAndDropFragment
import com.example.particles.ui.StandardModelFragment
import com.example.particles.ui.particles_list.ParticlesFragment


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class PageViewAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    // TODO use non-depecated class

    private val TAB_TITLES = arrayOf(
        "DRAGGER",
        "PARTICLES",
        "STANDARD MODEL"
    )

    private var updateData = false

    fun refreshData() {
        updateData = true
        notifyDataSetChanged()
    }

    // Aquesta funció indica el fragment que s'ha de posar
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> DragAndDropFragment() // ParticlesWithSimpleAdapterFragment()
            1 -> ParticlesFragment()
            2 -> StandardModelFragment()
            else -> throw IllegalStateException("There's only 3 tabs")
        }
    }

    override fun getItemPosition(fragment: Any): Int {
        // Quan és necessari fer un refresh (les dades s'han modificat), ho notifiquem al fragment
        // que cal
        if (updateData && fragment is ParticlesFragment) {
            updateData = false
            fragment.refreshData()
        }

        return super.getItemPosition(fragment)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return TAB_TITLES.size
    }
}