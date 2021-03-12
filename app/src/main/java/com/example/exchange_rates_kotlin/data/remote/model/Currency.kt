package com.example.exchange_rates_kotlin.data.remote.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root


@Root(strict = false, name = "Currency")
data class Currency(
    @field:Element(name = "NumCode", required = false)
    var NumCode: String = "",
    @field:Element(name = "CharCode", required = false)
    var CharCode: String = "",
    @field:Element(name = "Scale", required = false)
    var Scale: String = "",
    @field:Element(name = "Name", required = false)
    var Name: String = "",
    @field:Element(name = "Rate", required = false)
    var Rate: String = ""
)