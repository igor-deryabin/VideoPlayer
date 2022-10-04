package com.example.player.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channels")
data class FavoriteChannel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val channelId: Int,
    val name: String,
    val descShort: String,
    val descFull: String,
    val image: String,
    val url: String
)
