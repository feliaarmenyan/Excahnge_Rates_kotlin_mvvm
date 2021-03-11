package com.example.exchange_rates_kotlin.data.remote.api

import com.example.exchange_rates_kotlin.data.remote.model.DailyExRates
import retrofit2.http.GET

interface DailyExRatesApiService {

    @GET("03/12/202")
    suspend fun getDailyExRates(): DailyExRates
}