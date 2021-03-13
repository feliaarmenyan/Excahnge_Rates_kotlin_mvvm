package com.example.exchange_rates_kotlin.ui.exchagerates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.exchange_rates_kotlin.data.remote.model.Currency
import com.example.exchange_rates_kotlin.databinding.ItemExchangeRateBinding

class ExRatesAdapter () : ListAdapter<Currency, ExRatesAdapter.BaseViewHolder>(Currency.COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemExchangeRateBinding.inflate(layoutInflater, parent, false)
        return SearchItemViewHolder(binding)
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

    inner class SearchItemViewHolder(private var binding: ItemExchangeRateBinding) :
        BaseViewHolder(binding) {
        override fun bindTo(item: Currency) {
            with(binding) {
                charCode.text=item.CharCode
                exRates.text=item.exRates()
                manySizeFirstDay.text=item.Rate
                manySizeSecondDay.text=item.secondDay
            }
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}