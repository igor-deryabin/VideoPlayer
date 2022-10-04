package com.example.player.domain.usecase.channels

import com.example.player.domain.data.Channel
import com.example.player.domain.repository.ChannelsRepository
import com.example.player.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetAllChannelsUseCase(
    private val repository: ChannelsRepository
) {
    suspend operator fun invoke(): Flow<Result<List<Channel>>> {
        val result = repository.getAllChannels().map {
            it.toDomainModel()
        }
        return flow {
            result.collect {
                it.forEach { ch ->
                    ch.isFavorite = repository.isFavoriteChannel(ch.id) == true
                }
                emit(Result.success(it))
            }
        }
    }
}