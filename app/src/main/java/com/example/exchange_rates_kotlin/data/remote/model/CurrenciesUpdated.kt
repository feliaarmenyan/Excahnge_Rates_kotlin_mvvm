package com.example.exchange_rates_kotlin.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrenciesUpdated(
    val currencies: List<CurrencyPosition>? = null,
) : Parcelable {

    @Parcelize
    data class CurrencyPosition(
        val currency: String,
        val position: Int
    ) : Parcelable
}