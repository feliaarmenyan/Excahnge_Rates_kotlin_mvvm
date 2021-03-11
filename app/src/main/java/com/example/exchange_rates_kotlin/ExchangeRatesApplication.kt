package com.example.exchange_rates_kotlin

import android.app.Application
import androidx.viewbinding.BuildConfig
import com.example.exchange_rates_kotlin.di.appModule
import com.example.exchange_rates_kotlin.di.networkModule
import com.example.exchange_rates_kotlin.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class ExchangeRatesApplication  : Application() {

    override fun onCreate() {
        super.onCreate()
        // Adding Koin modules to our application
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@ExchangeRatesApplication)
            modules(listOf(appModule, networkModule,viewModelsModule))
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
