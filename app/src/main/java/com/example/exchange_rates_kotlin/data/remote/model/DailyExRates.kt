package com.example.exchange_rates_kotlin.data.remote.model

import java.util.*

data class DailyExRates(
    val Date: Date?=null,
    val Currency: List<Currency> ?=null
)
