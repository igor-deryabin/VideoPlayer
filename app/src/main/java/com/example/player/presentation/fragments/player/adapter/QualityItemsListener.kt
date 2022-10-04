package com.example.player.presentation.fragments.player.adapter

import com.example.player.domain.data.Quality

interface QualityItemsListener {
    fun changeQuality(item: Quality)
}