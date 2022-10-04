package com.example.player.di

import com.example.player.presentation.fragments.main.MainViewModel
import com.example.player.presentation.fragments.main.all.AllChannelsViewModel
import com.example.player.presentation.fragments.main.favorite.FavoritesChannelsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationAppModule = module {
    viewModel { MainViewModel() }
    viewModel { AllChannelsViewModel(channelsUseCases = get()) }
    viewModel { FavoritesChannelsViewModel(channelsUseCases = get()) }
}