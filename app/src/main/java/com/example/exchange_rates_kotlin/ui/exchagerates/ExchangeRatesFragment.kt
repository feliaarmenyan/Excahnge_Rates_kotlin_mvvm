package com.example.exchange_rates_kotlin.ui.exchagerates

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.exchange_rates_kotlin.R
import com.example.exchange_rates_kotlin.core.platform.viewbindingdelegate.viewBinding
import com.example.exchange_rates_kotlin.core.utils.*
import com.example.exchange_rates_kotlin.data.remote.model.DailyExRates
import com.example.exchange_rates_kotlin.databinding.FragmentExchangeRatesBinding
import com.example.exchange_rates_kotlin.ui.DailyRatesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class ExchangeRatesFragment : Fragment(R.layout.fragment_exchange_rates) {

    private val mBinding by viewBinding(FragmentExchangeRatesBinding::bind)
    private val mAdapter by lazy { ExRatesAdapter() }

    private val mViewModel by viewModel<DailyRatesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.loadTomorrowRates(requireContext().getDate(tomorrow()))
        mViewModel.loadTodayRates(requireContext().getDate(Date()))
        initViewModels()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.recyclerView.adapter = mAdapter

        mBinding.settings.setOnClickListener {
            findNavController()
                .navigate(R.id.action_ExchangeRatesFragment_to_SettingsFragment)
        }
    }

    private fun initViewModels() {
        mViewModel.todayExRates.observe(this) { items ->
            Log.e("todayy items ", items.toString())
            mBinding.viewError.root.gone(false)
            mAdapter.submitList(items.CurrencyList)
        }

        mViewModel.yesterdayExRates.observe(this) { items ->
            Log.e("yesterday items ", items.toString())
            with(mBinding) {
                firstDay.text = requireContext().getDate(Date())
                secondDay.text = requireContext().getDate(yesterday())
            }
            setSecondDayInfo(items)
        }

        mViewModel.tomorrowExRates.observe(this) { items ->
            Log.e("Tomorrow items ", items.toString())
            with(mBinding) {
                firstDay.text = requireContext().getDate(Date())
                secondDay.text = requireContext().getDate(tomorrow())
            }

            setSecondDayInfo(items)
        }

        mViewModel.showError.observe(this) { error ->
            Log.e("today error  ", error)

            showErrorView()
        }

        mViewModel.showTomorrowError.observe(this) { error ->
            Log.e("tomorrow error  ", error)
            mViewModel.loadYesterdayRates(requireContext().getDate(yesterday()))

            showErrorView()
        }

        mViewModel.showYesterdayError.observe(this) { error ->
            Log.e("tomorrow error  ", error)

            showErrorView()
        }
    }

    private fun showErrorView() {
        if (mViewModel.showError.value != null) {
            with(mBinding) {
                mBinding.settings.invisible(false)
                viewError.root.visible(false)
            }
        }
    }

    private fun setSecondDayInfo(exRates: DailyExRates){

        mViewModel.todayExRates.value?.CurrencyList?.let { currencies ->
            if (currencies.isNotEmpty() && !exRates.CurrencyList.isNullOrEmpty()) {
                for (todayCurrency in currencies) {
                    for (currency in exRates.CurrencyList!!) {
                        if (todayCurrency.NumCode == currency.NumCode) {
                            todayCurrency.secondDay = currency.Rate
                        }
                    }
                }
            }
            mAdapter.clearList()
            mAdapter.submitList(currencies)
        }
    }
}