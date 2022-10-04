package com.example.player.data.remote

import com.example.player.data.remote.model.ChannelsResponse
import retrofit2.http.GET


interface AppApi {
    @GET("playlist/channels.json")
    suspend fun getAllChannels(): ChannelsResponse
}