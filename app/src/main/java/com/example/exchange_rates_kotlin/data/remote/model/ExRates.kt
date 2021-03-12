package com.example.exchange_rates_kotlin.data.remote.model

sealed class ExRates
class FirstDay(var firstDay: List<Currency>?) : ExRates()
class SecondDay(var secondDay: List<Currency>?) : ExRates()
