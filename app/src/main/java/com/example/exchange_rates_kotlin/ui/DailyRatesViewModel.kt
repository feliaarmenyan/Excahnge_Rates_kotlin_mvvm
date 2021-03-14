package com.example.exchange_rates_kotlin.ui

import androidx.lifecycle.MutableLiveData
import com.example.exchange_rates_kotlin.core.platform.BaseViewModel
import com.example.exchange_rates_kotlin.core.utils.SingleLiveEvent
import com.example.exchange_rates_kotlin.data.UseCaseResult
import com.example.exchange_rates_kotlin.data.remote.model.DailyExRates
import com.example.exchange_rates_kotlin.data.remote.repository.DailyExRatesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DailyRatesViewModel(private val repository: DailyExRatesRepository) : BaseViewModel() {

    val todayExRates = MutableLiveData<DailyExRates>()
    val yesterdayExRates = MutableLiveData<DailyExRates>()
    val tomorrowExRates = MutableLiveData<DailyExRates>()
    val showYesterdayError = SingleLiveEvent<String>()
    val showTomorrowError = SingleLiveEvent<String>()
    val showError = SingleLiveEvent<String>()
    val loading = MutableLiveData<Boolean>()

    fun loadTodayRates(day: String?) {
        loading.value = true
        launch {
            val result = withContext(Dispatchers.IO) { repository.getDailyExRates(day) }
            loading.value = false
            when (result) {
                is UseCaseResult.Success -> todayExRates.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun loadYesterdayRates(day: String?) {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getDailyExRates(day) }
            when (result) {
                is UseCaseResult.Success -> yesterdayExRates.value = result.data
                is UseCaseResult.Error -> showYesterdayError.value = result.exception.message
            }
        }
    }

    fun loadTomorrowRates(day: String?) {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getDailyExRates(day) }
            when (result) {
                is UseCaseResult.Success -> tomorrowExRates.value = result.data
                is UseCaseResult.Error -> showTomorrowError.value = result.exception.message
            }
        }
    }
}