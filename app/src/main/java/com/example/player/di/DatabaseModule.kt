package com.example.player.di

import android.app.Application
import androidx.room.Room
import com.example.player.data.local.ChannelsDao
import com.example.player.data.local.MainDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    fun provideDatabase(application: Application): MainDatabase {
        return Room.databaseBuilder(application, MainDatabase::class.java, "main_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideChannelsDao(database: MainDatabase): ChannelsDao {
        return  database.channelsDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideChannelsDao(get()) }
}