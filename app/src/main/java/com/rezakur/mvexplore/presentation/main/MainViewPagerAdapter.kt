package com.rezakur.mvexplore.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rezakur.mvexplore.presentation.movie.MovieFragment
import com.rezakur.mvexplore.presentation.tvshow.TvShowFragment

class MainViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return MovieFragment()
            1 -> return TvShowFragment()
        }

        return MovieFragment()
    }
}