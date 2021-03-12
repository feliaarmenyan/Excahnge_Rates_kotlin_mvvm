package com.example.exchange_rates_kotlin.ui

import androidx.lifecycle.MutableLiveData
import com.example.exchange_rates_kotlin.core.platform.BaseViewModel
import com.example.exchange_rates_kotlin.core.utils.SingleLiveEvent
import com.example.exchange_rates_kotlin.data.UseCaseResult
import com.example.exchange_rates_kotlin.data.remote.model.DailyExRates
import com.example.exchange_rates_kotlin.data.remote.model.ExRates
import com.example.exchange_rates_kotlin.data.remote.repository.DailyExRatesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DailyRatesViewModel(private val repository: DailyExRatesRepository) : BaseViewModel() {

    val exRates = MutableLiveData<DailyExRates>()
    val showError = SingleLiveEvent<String>()

    fun loadItems() {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getDailyExRates("03.12.2020") }
            when (result) {
                is UseCaseResult.Success -> exRates.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }
}