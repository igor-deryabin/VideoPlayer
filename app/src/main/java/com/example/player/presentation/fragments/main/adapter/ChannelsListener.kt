package com.example.player.presentation.fragments.main.adapter

import com.example.player.domain.data.Channel

interface ChannelsListener {
    fun openPlayer(item: Channel)
    fun toggleFavorite(item: Channel)
}