package com.example.sampleproject

import android.app.Application
import com.example.sampleproject.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)

            androidContext(this@App)

            modules(listOf(appModule))
        }
    }

}