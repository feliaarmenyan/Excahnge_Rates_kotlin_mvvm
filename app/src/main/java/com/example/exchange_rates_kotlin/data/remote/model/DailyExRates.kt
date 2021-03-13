package com.example.exchange_rates_kotlin.data.remote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Parcelize
@Root(strict = false, name = "DailyExRates")
data class DailyExRates(
    @field:ElementList(
        required = false,
        name = "Currency",
        entry = "Currency",
        inline = true,
        empty = true
    )
    var CurrencyList: MutableList<Currency>? = null
) : Parcelable