package com.example.exchange_rates_kotlin.ui.exchagerates

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.exchange_rates_kotlin.R
import com.example.exchange_rates_kotlin.core.platform.viewbindingdelegate.viewBinding
import com.example.exchange_rates_kotlin.core.utils.*
import com.example.exchange_rates_kotlin.data.local.preference.AppPreferences
import com.example.exchange_rates_kotlin.data.remote.model.Currency
import com.example.exchange_rates_kotlin.data.remote.model.DailyExRates
import com.example.exchange_rates_kotlin.databinding.FragmentExchangeRatesBinding
import com.example.exchange_rates_kotlin.ui.DailyRatesViewModel
import com.example.exchange_rates_kotlin.ui.settings.IS_CHANGED_SELECTED_ITEMS
import com.example.exchange_rates_kotlin.ui.settings.SettingsFragmentArgs
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class ExchangeRatesFragment : Fragment(R.layout.fragment_exchange_rates) {

    private val mBinding by viewBinding(FragmentExchangeRatesBinding::bind)
    private val mAdapter by lazy { ExRatesAdapter() }

    private var shownCurrencyList = arrayListOf<Currency>()

    private val mViewModel by viewModel<DailyRatesViewModel>()

    private val appPreference by inject<AppPreferences>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.loadTodayRates(getDate(Date()))
        initViewModels()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(mBinding) {
            recyclerView.adapter = mAdapter
            (recyclerView.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
            swipeRefresh.setOnRefreshListener {
                mViewModel.loadTodayRates(getDate(Date()))
            }

            firstDay.text = getDate(Date())
            mViewModel.tomorrowExRates.value?.let {
                secondDay.text = getDate(tomorrow())
            } ?: run {
                secondDay.text = getDate(yesterday())
            }
        }

        mBinding.settings.setOnClickListener {
            val info = mViewModel.todayExRates.value
            if (info != null && !info.CurrencyList.isNullOrEmpty()) {

                val args = SettingsFragmentArgs(info)
                findNavController().navigate(
                    R.id.action_ExchangeRatesFragment_to_SettingsFragment,
                    args.toBundle()
                )
            }
        }

        getNavigationResult<Boolean>(IS_CHANGED_SELECTED_ITEMS)?.observe(viewLifecycleOwner, {
            setSelectedItems(mViewModel.todayExRates.value)
        })
    }

    private fun initViewModels() {
        mViewModel.todayExRates.observe(this) { item ->
            mViewModel.loadTomorrowRates(getDate(tomorrow()))
            mBinding.viewError.root.gone(false)

            setSelectedItems(item)
        }

        mViewModel.yesterdayExRates.observe(this) { items ->
            setSecondDayInfo(items)
        }

        mViewModel.tomorrowExRates.observe(this) { items ->

            setSecondDayInfo(items)
        }

        mViewModel.showError.observe(this) { error ->
            Log.e("today error  ", error)
            showErrorView()
        }

        mViewModel.showTomorrowError.observe(this) { error ->
            Log.e("tomorrow error  ", error)
            mViewModel.loadYesterdayRates(getDate(yesterday()))

            showErrorView()
        }

        mViewModel.showYesterdayError.observe(this) { error ->
            Log.e("yesterday error  ", error)
            showErrorView()
        }

        mViewModel.loading.observe(this) {
            mBinding.swipeRefresh.isRefreshing = it
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

    private fun setSecondDayInfo(exRates: DailyExRates) {

        if (shownCurrencyList.isNotEmpty() && !exRates.CurrencyList.isNullOrEmpty()) {
            for (currency in exRates.CurrencyList!!) {

                mViewModel.todayExRates.value?.CurrencyList.let { currencyList ->
                    if (!currencyList.isNullOrEmpty()) {
                        for (todayCurrencyPos in 0 until currencyList.size) {
                            if (currencyList[todayCurrencyPos].NumCode == currency.NumCode) {
                                currencyList[todayCurrencyPos].secondDay = currency.Rate
                            }
                        }
                    }
                }

                for (todayCurrency in shownCurrencyList) {
                    if (todayCurrency.NumCode == currency.NumCode) {
                        todayCurrency.secondDay = currency.Rate
                    }
                }
            }
        }
        mAdapter.clearList()
        mAdapter.submitList(shownCurrencyList)
    }

    private fun setSelectedItems(item: DailyExRates?) {

        shownCurrencyList = arrayListOf()

        if (appPreference.selectedExRates == "") {
            if (!item?.CurrencyList.isNullOrEmpty()) {
                for (currencyPos in 0 until item?.CurrencyList!!.size) {
                    if (item.CurrencyList!![currencyPos].CharCode == "RUB" ||
                        item.CurrencyList!![currencyPos].CharCode == "EUR" ||
                        item.CurrencyList!![currencyPos].CharCode == "USD"
                    ) {
                        item.CurrencyList!![currencyPos].isChecked = true
                        shownCurrencyList.add(item.CurrencyList!![currencyPos])
                    }
                }
            }
        } else {
            val numCodeList: List<String> = (appPreference.selectedExRates.split(";"))

            if (!item?.CurrencyList.isNullOrEmpty() && numCodeList.isNotEmpty()) {
                for (currencyPos in 0 until item?.CurrencyList!!.size) {
                    for (numCode in numCodeList) {
                        if (item.CurrencyList!![currencyPos].NumCode == numCode) {
                            item.CurrencyList!![currencyPos].isChecked = true
                            shownCurrencyList.add(item.CurrencyList!![currencyPos])
                        }
                    }
                }
            }
        }

        mAdapter.clearList()
        mAdapter.submitList(shownCurrencyList)
    }
}