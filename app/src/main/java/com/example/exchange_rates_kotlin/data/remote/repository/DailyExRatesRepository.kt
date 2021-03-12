package com.example.exchange_rates_kotlin.data.remote.repository

import com.example.exchange_rates_kotlin.data.UseCaseResult
import com.example.exchange_rates_kotlin.data.remote.api.DailyExRatesApiService
import com.example.exchange_rates_kotlin.data.remote.model.ExRates
import com.example.exchange_rates_kotlin.data.remote.model.FirstDay
import com.example.exchange_rates_kotlin.data.remote.model.SecondDay

interface DailyExRatesRepository {

    suspend fun getDailyExRates(): UseCaseResult<List<ExRates>>
}

class AlbumRepositoryImpl(private val dailyResApi: DailyExRatesApiService) :
    DailyExRatesRepository {

    override suspend fun getDailyExRates(): UseCaseResult<List<ExRates>> {
        return try {
            val result = mutableListOf<ExRates>()
            val firstDayRates =
                dailyResApi.getFirstDayExRates()
            result.add(FirstDay(firstDayRates.Currency))
            val secondDayRates =
                dailyResApi.getSecondDayExRates()
            result.add(SecondDay(secondDayRates.Currency))
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }

}