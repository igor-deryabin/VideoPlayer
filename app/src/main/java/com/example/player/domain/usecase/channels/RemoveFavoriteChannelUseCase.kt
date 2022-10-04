package com.example.player.domain.usecase.channels

import com.example.player.domain.repository.ChannelsRepository

class RemoveFavoriteChannelUseCase(
    private val repository: ChannelsRepository
) {
    suspend operator fun invoke(channelId: Int) {
        repository.removeFavoriteChannel(channelId)
    }
}