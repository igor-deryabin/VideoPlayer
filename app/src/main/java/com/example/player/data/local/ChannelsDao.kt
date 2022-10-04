package com.example.player.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.player.data.local.model.FavoriteChannel

@Dao
interface ChannelsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(channel: FavoriteChannel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(channels: List<FavoriteChannel>)

    @Query("SELECT EXISTS (SELECT 1 FROM channels WHERE channelId = :channelId)")
    suspend fun exists(channelId: Int): Boolean

    @Query("SELECT * FROM channels")
    suspend fun getAll(): List<FavoriteChannel>

    @Query("SELECT * FROM channels WHERE channelId = :channelId")
    suspend fun getChannelFromId(channelId: Int): FavoriteChannel?

    @Query("DELETE FROM channels WHERE channelId = :channelId")
    suspend fun removeChannel(channelId: Int)
}