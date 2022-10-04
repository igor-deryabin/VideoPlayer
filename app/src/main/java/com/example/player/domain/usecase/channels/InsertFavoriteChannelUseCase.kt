package com.example.player.domain.usecase.channels

import com.example.player.data.local.model.FavoriteChannel
import com.example.player.domain.data.Channel
import com.example.player.domain.repository.ChannelsRepository

class InsertFavoriteChannelUseCase(
    private val repository: ChannelsRepository
) {
    suspend operator fun invoke(channel: Channel) {
        val favoriteChannel = FavoriteChannel(
            channelId = channel.id,
            name = channel.name,
            descShort = channel.descShort,
            descFull = channel.descFull,
            image = channel.image,
            url = channel.url
        )
        repository.insertFavoriteChannel(favoriteChannel)
    }
}