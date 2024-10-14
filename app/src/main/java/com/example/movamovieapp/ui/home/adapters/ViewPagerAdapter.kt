package com.example.movamovieapp.ui.home.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movamovieapp.ui.home.tabs.MovieLikeThisFragment
import com.example.movamovieapp.ui.home.tabs.TrailersFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val id: String
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TrailersFragment.newInstance(id)
            1 -> MovieLikeThisFragment(id)
            else -> TrailersFragment.newInstance(id)
        }
    }

}