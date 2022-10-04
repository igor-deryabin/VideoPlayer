package com.example.player.presentation.fragments.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.player.R
import com.example.player.databinding.ItemChannelBinding
import com.example.player.domain.data.Channel

open class ChannelVH(
    private val binding: ItemChannelBinding,
    private val context: Context,
    private val listener: ChannelsListener
): RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun setData(item: Channel) {
        binding.apply {
            toggleStar(item.isFavorite)

            tvTitle.text = item.name
            tvSubtitle.text = item.descShort

            Glide.with(context)
                .load(item.image)
                .into(imageView)

            clItem.setOnClickListener { listener.openPlayer(item) }
            rlFavorite.setOnClickListener {
                item.isFavorite = !item.isFavorite
                listener.toggleFavorite(item)
                toggleStar(item.isFavorite)
            }
        }
    }

    private fun toggleStar(isActive: Boolean) {
        binding.apply {
            if (isActive) {
                imageStar.setImageResource(R.drawable.icon_star_active)
            } else {
                imageStar.setImageResource(R.drawable.icon_star_inactive)
            }
        }
    }
}