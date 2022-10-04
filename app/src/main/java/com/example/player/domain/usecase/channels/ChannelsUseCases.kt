package com.example.player.domain.usecase.channels

data class ChannelsUseCases(
    val getAllChannels: GetAllChannelsUseCase,
    val getFavoriteChannels: GetFavoriteChannelsUseCase,
    val insertFavoriteChannel: InsertFavoriteChannelUseCase,
    val removeFavoriteChannel: RemoveFavoriteChannelUseCase
)