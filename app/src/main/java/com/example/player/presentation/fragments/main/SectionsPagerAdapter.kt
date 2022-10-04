package com.example.player.presentation.fragments.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.player.presentation.fragments.main.all.AllChannelsFragment
import com.example.player.presentation.fragments.main.favorite.FavoritesChannelsFragment


class SectionsPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> AllChannelsFragment()
            1 ->  FavoritesChannelsFragment()
            else -> Fragment()
        }
    }

    fun findFragmentAtPosition(
        fragmentManager: FragmentManager,
        position: Int
    ): Fragment? {
        return fragmentManager.findFragmentByTag("f$position")
    }
}