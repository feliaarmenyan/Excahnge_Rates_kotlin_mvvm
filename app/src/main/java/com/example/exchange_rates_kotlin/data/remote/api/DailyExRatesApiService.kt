package com.example.exchange_rates_kotlin.data.remote.api

import com.example.exchange_rates_kotlin.data.remote.model.DailyExRates
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface DailyExRatesApiService {

    @GET("XmlExRates.aspx")
    suspend fun getFirstDayExRates(@Query("ondate") Date: String?): DailyExRates
}