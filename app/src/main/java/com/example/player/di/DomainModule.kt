package com.example.player.di

import com.example.player.data.repository.ChannelsRepositoryImpl
import com.example.player.domain.repository.ChannelsRepository
import com.example.player.domain.usecase.channels.*
import org.koin.dsl.module

val domainAppModule = module {
    single<ChannelsRepository> { ChannelsRepositoryImpl(api = get(), dao = get()) }

    single<ChannelsUseCases> {
        ChannelsUseCases(
            getAllChannels = GetAllChannelsUseCase(repository = get()),
            getFavoriteChannels = GetFavoriteChannelsUseCase(repository = get()),
            insertFavoriteChannel = InsertFavoriteChannelUseCase(repository = get()),
            removeFavoriteChannel = RemoveFavoriteChannelUseCase(repository = get())
        )
    }
}
