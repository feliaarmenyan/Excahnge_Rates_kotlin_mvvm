package com.example.exchange_rates_kotlin.di

import com.example.exchange_rates_kotlin.data.local.preference.AppPreferences
import com.example.exchange_rates_kotlin.data.local.preference.AppPreferencesImpl
import com.example.exchange_rates_kotlin.data.remote.repository.DailyExRatesRepository
import com.example.exchange_rates_kotlin.data.remote.repository.DailyExRatesRepositoryImpl
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {

    single { GsonBuilder().create() }

    single<AppPreferences> { AppPreferencesImpl(context = androidApplication()) }

    single<DailyExRatesRepository> { DailyExRatesRepositoryImpl(dailyResApi = get()) }
}