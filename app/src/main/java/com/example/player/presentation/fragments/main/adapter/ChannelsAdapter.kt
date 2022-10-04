package com.example.player.presentation.fragments.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.player.databinding.ItemChannelBinding
import com.example.player.domain.data.Channel
import com.example.player.utils.RecyclerViewAdapterBase

class ChannelsAdapter(
    private val context: Context,
    private val listener: ChannelsListener,
    items: List<Channel>
): RecyclerViewAdapterBase<Channel, ChannelVH>() {

    init {
        addAll(items)
    }

    override fun initViewHolder(holder: ChannelVH) {

    }

    override fun setModel(holder: ChannelVH, model: Channel) {
        holder.setData(model)
    }

    override fun createView(parent: ViewGroup, viewType: Int): ChannelVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChannelBinding.inflate(inflater, parent, false)
        return ChannelVH(binding, context, listener)
    }
}