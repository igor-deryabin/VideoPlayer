package com.example.player.domain.repository

import com.example.player.data.local.model.FavoriteChannel
import com.example.player.data.remote.model.ChannelsResponse
import kotlinx.coroutines.flow.Flow


interface ChannelsRepository {
    suspend fun getAllChannels(): Flow<ChannelsResponse>
    suspend fun getFavoriteChannels(): Flow<List<FavoriteChannel>>
    suspend fun isFavoriteChannel(channelId: Int): Boolean
    suspend fun insertFavoriteChannel(channel: FavoriteChannel)
    suspend fun removeFavoriteChannel(channelId: Int)
}