package com.example.exchange_rates_kotlin.data.remote.model

data class Currency(
    val Id: String ?=null,
    val NumCode: String ?=null,
    val CharCode: String ?=null,
    val Scale: String ?=null,
    val Name: String ?=null,
    val Rate: String ?=null,
    @Transient
    val RateSecondDay: String ?=null,
    )
