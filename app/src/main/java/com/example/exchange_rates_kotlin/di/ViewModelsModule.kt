package com.example.exchange_rates_kotlin.di

import com.example.exchange_rates_kotlin.ui.DailyRatesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel { DailyRatesViewModel(repository = get()) }
}