package com.example.exchange_rates_kotlin.data.remote.repository

import com.example.exchange_rates_kotlin.data.UseCaseResult
import com.example.exchange_rates_kotlin.data.remote.api.DailyExRatesApiService
import com.example.exchange_rates_kotlin.data.remote.model.DailyExRates

interface DailyExRatesRepository {

    suspend fun getDailyExRates(Date: String?): UseCaseResult<DailyExRates>
}

class DailyExRatesRepositoryImpl(private val dailyResApi: DailyExRatesApiService) :
    DailyExRatesRepository {

    override suspend fun getDailyExRates(Date: String?): UseCaseResult<DailyExRates> {
        return try {
            val result = dailyResApi.getFirstDayExRates(Date)
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }
}