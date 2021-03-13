package com.example.exchange_rates_kotlin.ui.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.exchange_rates_kotlin.data.remote.model.Currency
import com.example.exchange_rates_kotlin.databinding.ItemExchangeRateBinding
import com.example.exchange_rates_kotlin.databinding.ItemSettingsBinding

class SettingsAdapter() :
    ListAdapter<Currency, SettingsAdapter.BaseViewHolder>(Currency.COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSettingsBinding.inflate(layoutInflater, parent, false)
        return SettingsItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    abstract class BaseViewHolder(viewDataBinding: ViewBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        abstract fun bindTo(item: Currency)
    }

    fun clearList() {
        submitList(mutableListOf())
        notifyDataSetChanged()
    }

    inner class SettingsItemViewHolder(private var binding: ItemSettingsBinding) :
        BaseViewHolder(binding) {
        override fun bindTo(item: Currency) {
            with(binding) {
                charCode.text = item.CharCode
                exRates.text = item.exRates()
                when (item.CharCode) {
                    "USD" -> exchangeSwitch.isChecked = true
                    "EUR" -> exchangeSwitch.isChecked = true
                    "RU" -> exchangeSwitch.isChecked = true
                    else -> exchangeSwitch.isChecked = false
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}