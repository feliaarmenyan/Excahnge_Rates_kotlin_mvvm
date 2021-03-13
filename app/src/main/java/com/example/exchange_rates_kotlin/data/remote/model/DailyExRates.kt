package com.example.exchange_rates_kotlin.data.remote.model

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(strict = false, name = "DailyExRates")
data class DailyExRates constructor(
    @field:ElementList(
        required = false,
        name = "Currency",
        entry = "Currency",
        inline = true,
        empty = true
    )
    var CurrencyList: MutableList<Currency>? = null
)