package com.example.exchange_rates_kotlin.ui.settings

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.exchange_rates_kotlin.data.remote.model.CurrenciesUpdated
import com.example.exchange_rates_kotlin.data.remote.model.Currency
import com.example.exchange_rates_kotlin.databinding.ItemSettingsBinding

class SettingsSwapAdapter(
    private var items: MutableList<Currency>
) : RecyclerView.Adapter<SettingsSwapAdapter.BaseHomeViewHolder>() {
    var binding: ItemSettingsBinding? = null

//    var onTrackItemClick: ((Int, Currency, View) -> Unit)? = null

    fun setItems(elements: List<Currency>) {
        val size = items.size
        this.items.clear()
        notifyItemRangeRemoved(0, size)
        items.addAll(elements)
        notifyItemRangeChanged(0, items.size)
    }

    fun addItems(elements: List<Currency>) {
        val positionStart: Int = this.items.size
        this.items.addAll(elements)
        notifyItemRangeChanged(positionStart, elements.size)
    }

    fun getMoverView(): ImageView? {
        return binding?.mover
    }

    /**
     * Function called to swap dragged items
     */
    fun swapItems(
        fromPosition: Int,
        toPosition: Int
    ): List<CurrenciesUpdated.CurrencyPosition> {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                items[i] = items.set(i + 1, items[i]);
            }
        } else {
            for (i in fromPosition..toPosition + 1) {
                items[i] = items.set(i - 1, items[i]);
            }
        }

        notifyItemMoved(fromPosition, toPosition)
        val trackPositions = mutableListOf<CurrenciesUpdated.CurrencyPosition>()
        items.toList().forEachIndexed { index, currency ->
            trackPositions.add(CurrenciesUpdated.CurrencyPosition(currency.NumCode, index))
        }

        return trackPositions
    }

    fun getItems(): ArrayList<Currency> {
        return items as ArrayList<Currency>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHomeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ItemSettingsBinding.inflate(layoutInflater, parent, false)
        return TrackViewHolder(binding!!)
    }

    abstract class BaseHomeViewHolder(viewDataBinding: ViewBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        abstract fun bindTo(currency: Currency)
    }


    override fun onBindViewHolder(holder: BaseHomeViewHolder, position: Int) {
        holder.bindTo(items[position])
    }

    inner class TrackViewHolder(private var binding: ItemSettingsBinding) :
        BaseHomeViewHolder(binding) {
        override fun bindTo(currency: Currency) {
            with(binding) {
                charCode.text = currency.CharCode
                exRates.text = currency.exRates()
                when (currency.CharCode) {
                    "USD" -> exchangeSwitch.isChecked = true
                    "EUR" -> exchangeSwitch.isChecked = true
                    "RUB" -> exchangeSwitch.isChecked = true
                    else -> exchangeSwitch.isChecked = false
                }
            }
        }
    }

    override fun getItemCount(): Int =
        items.size
}