package com.example.player.presentation.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.player.R
import com.example.player.databinding.FragmentMainBinding
import com.example.player.presentation.fragments.main.all.AllChannelsFragment
import com.example.player.presentation.fragments.main.favorite.FavoritesChannelsFragment
import com.google.android.material.tabs.TabLayoutMediator
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment: Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val vm: MainViewModel by viewModel()

    private var sectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        KeyboardVisibilityEvent.setEventListener(requireActivity(), viewLifecycleOwner) {
            if (!it) {
                binding.etFilter.clearFocus()
            }
        }

        binding.apply {
            sectionsPagerAdapter = SectionsPagerAdapter(this@MainFragment)
            viewPager.adapter = sectionsPagerAdapter
            viewPager.isUserInputEnabled = false

            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> getString(R.string.main_tabs_all)
                    1 -> getString(R.string.main_tabs_favorites)
                    else -> getString(R.string.main_tabs_all)
                }
            }.attach()

            etFilter.doOnTextChanged { text, _, _, _ ->
                updateFilter(text?.toString() ?: "")
            }
        }
    }

    private fun updateFilter(filter: String) {
        val firstTabFragment = sectionsPagerAdapter?.findFragmentAtPosition(childFragmentManager,0)
        val secondTabFragment = sectionsPagerAdapter?.findFragmentAtPosition(childFragmentManager,1)
        if (firstTabFragment != null && firstTabFragment is AllChannelsFragment) {
            firstTabFragment.setFilter(filter)
        }
        if (secondTabFragment != null && secondTabFragment is FavoritesChannelsFragment) {
            secondTabFragment.setFilter(filter)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}