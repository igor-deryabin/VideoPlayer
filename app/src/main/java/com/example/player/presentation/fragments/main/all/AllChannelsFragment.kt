package com.example.player.presentation.fragments.main.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.player.R
import com.example.player.databinding.FragmentAllChannelsBinding
import com.example.player.domain.data.Channel
import com.example.player.presentation.fragments.main.adapter.ChannelsAdapter
import com.example.player.presentation.fragments.main.adapter.ChannelsListener
import com.example.player.utils.Result
import com.example.player.utils.extension.longToast
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class AllChannelsFragment: Fragment(), ChannelsListener {

    private var _binding: FragmentAllChannelsBinding? = null
    private val binding get() = _binding!!

    private val vm: AllChannelsViewModel by viewModel()

    private var adapter: ChannelsAdapter? = null
    private val channels = mutableListOf<Channel>()
    private var filter: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAllChannelsBinding.inflate(inflater, container, false)
        val root = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.error.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                longToast(it)
            }
        }
        vm.channels.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Result.Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        result.data?.let {
                            channels.clear()
                            channels.addAll(it)
                            updateChannels()
                        }
                    }
                    Result.Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        result.error?.let {
                            it.message?.let { it1 -> longToast(it1) }
                        }
                    }
                    Result.Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }

        adapter = ChannelsAdapter(
            requireContext(),
            this@AllChannelsFragment,
            emptyList()
        )

        binding.apply {
            rvItems.apply {
                isNestedScrollingEnabled = false
            }.adapter = adapter
        }
    }

    fun setFilter(newFilter: String) {
        filter = newFilter
        updateChannels()
    }

    override fun onResume() {
        super.onResume()
        vm.getChannelsList()
    }

    private fun updateChannels() {
        if (filter.isEmpty()) {
            adapter?.clearAndAddAll(channels)
        } else {
            adapter?.clearAndAddAll(channels.filter {
                it.name.lowercase(Locale.getDefault())
                    .contains(filter.lowercase(Locale.getDefault()))
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun openPlayer(item: Channel) {
        val bundle = Bundle().apply {
            putSerializable("channel", item)
        }

        findNavController().navigate(R.id.action_mainFragment_to_playerFragment, bundle)
    }

    override fun toggleFavorite(item: Channel) {
        vm.updateChannel(item)
    }
}