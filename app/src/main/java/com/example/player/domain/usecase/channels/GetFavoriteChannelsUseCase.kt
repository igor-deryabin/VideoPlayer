package com.example.player.domain.usecase.channels

import com.example.player.domain.data.Channel
import com.example.player.domain.repository.ChannelsRepository
import com.example.player.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFavoriteChannelsUseCase(
    private val repository: ChannelsRepository
) {
    suspend operator fun invoke(): Flow<Result<List<Channel>>> {
        return repository.getFavoriteChannels().map {
            Result.success(it.map { f ->
                Channel(
                    id = f.channelId,
                    name = f.name,
                    descShort = f.descShort,
                    descFull = f.descFull,
                    image = f.image,
                    url = f.url,
                    isFavorite = true
                )
            })
        }
    }
}