package com.example.player

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.player.di.databaseModule
import com.example.player.di.domainAppModule
import com.example.player.di.networkModule
import com.example.player.di.presentationAppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.logger.Level
import timber.log.Timber

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        GlobalContext.startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyApp)
            modules(networkModule, databaseModule, presentationAppModule, domainAppModule)
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}