package com.example.player.data.repository

import com.example.player.data.local.ChannelsDao
import com.example.player.data.local.model.FavoriteChannel
import com.example.player.data.remote.AppApi
import com.example.player.data.remote.model.ChannelsResponse
import com.example.player.domain.repository.ChannelsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import timber.log.Timber

class ChannelsRepositoryImpl(
    private val api: AppApi,
    private val dao: ChannelsDao
): ChannelsRepository {
    override suspend fun getAllChannels(): Flow<ChannelsResponse> {
        return flow {
            emit(api.getAllChannels())
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getFavoriteChannels(): Flow<List<FavoriteChannel>> {
        return flow {
            emit(dao.getAll())
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun isFavoriteChannel(channelId: Int): Boolean {
        return dao.exists(channelId)
    }

    override suspend fun insertFavoriteChannel(channel: FavoriteChannel) {
        try {
            withContext(Dispatchers.IO) { dao.insert(channel) }
        } catch (e: Exception) {
            Timber.e(e.message)
        }
    }

    override suspend fun removeFavoriteChannel(channelId: Int) {
        try {
            withContext(Dispatchers.IO) { dao.removeChannel(channelId) }
        } catch (e: Exception) {
            Timber.e(e.message)
        }
    }
}