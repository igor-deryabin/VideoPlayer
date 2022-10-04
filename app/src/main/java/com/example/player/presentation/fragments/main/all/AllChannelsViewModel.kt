package com.example.player.presentation.fragments.main.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.player.domain.data.Channel
import com.example.player.domain.usecase.channels.ChannelsUseCases
import com.example.player.utils.Event
import com.example.player.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AllChannelsViewModel(
    private val channelsUseCases: ChannelsUseCases
) : ViewModel() {

    private var _error = MutableLiveData<Event<String>>()
    var error: LiveData<Event<String>> = _error

    private var _channels = MutableLiveData<Event<Result<List<Channel>>>>()
    var channels: LiveData<Event<Result<List<Channel>>>> = _channels

    fun getChannelsList() {
        viewModelScope.launch(Dispatchers.IO) {
            channelsUseCases.getAllChannels()
                .onStart { emit(Result.loading()) }
                .catch { e ->
                    _error.value = Event(e.message ?: "")
                }
                .collect {
                    withContext(Dispatchers.Main) {
                        _channels.value = Event(it)
                    }
                }
        }
    }

    fun updateChannel(channel: Channel) {
        viewModelScope.launch(Dispatchers.IO) {
            if (channel.isFavorite) {
                channelsUseCases.insertFavoriteChannel(channel)
            } else {
                channelsUseCases.removeFavoriteChannel(channel.id)
            }
        }
    }
}