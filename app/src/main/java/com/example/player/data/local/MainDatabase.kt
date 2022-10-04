package com.example.player.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.player.data.local.model.FavoriteChannel


@Database(
    entities = [(FavoriteChannel::class)],
    version = 1, exportSchema = false
)
abstract class MainDatabase: RoomDatabase() {
    abstract val channelsDao: ChannelsDao
}