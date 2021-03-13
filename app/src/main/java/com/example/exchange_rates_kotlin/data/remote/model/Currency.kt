package com.example.exchange_rates_kotlin.data.remote.model

import androidx.recyclerview.widget.DiffUtil
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
    var Rate: String = "",
    @Transient
    var secondDay: String = ""
) {
    fun exRates(): String {
        return "$Scale  $Name"
    }

    companion object {
        val COMPARATOR: DiffUtil.ItemCallback<Currency> =
            object : DiffUtil.ItemCallback<Currency>() {
                override fun areItemsTheSame(
                    oldItem: Currency,
                    newItem: Currency,
                ): Boolean {
                    return oldItem.NumCode == newItem.NumCode
                }

                override fun areContentsTheSame(
                    oldItem: Currency,
                    newItem: Currency,
                ): Boolean {
                    return oldItem == newItem;

                }
            }
    }

}
