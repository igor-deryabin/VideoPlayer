package com.example.player.presentation.fragments.player.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.player.databinding.ItemQualityBinding
import com.example.player.domain.data.Quality
import com.example.player.utils.RecyclerViewAdapterBase

class QualityItemsAdapter(
    private val context: Context,
    private val listener: QualityItemsListener,
    private val newItems: List<Quality>
): RecyclerViewAdapterBase<Quality, QualityItemVH>() {

    init {
        addAll(newItems)
    }

    override fun initViewHolder(holder: QualityItemVH) {

    }

    override fun setModel(holder: QualityItemVH, model: Quality) {
        holder.setData(model)
    }

    override fun createView(parent: ViewGroup, viewType: Int): QualityItemVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemQualityBinding.inflate(inflater, parent, false)
        return QualityItemVH(binding, context, listener, newItems.size)
    }
}