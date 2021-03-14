package com.example.exchange_rates_kotlin.ui.settings

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.exchange_rates_kotlin.R
import com.example.exchange_rates_kotlin.core.platform.viewbindingdelegate.viewBinding
import com.example.exchange_rates_kotlin.core.utils.setNavigationResult
import com.example.exchange_rates_kotlin.data.local.preference.AppPreferences
import com.example.exchange_rates_kotlin.data.remote.model.Currency
import com.example.exchange_rates_kotlin.databinding.FragmentSettingsBinding
import org.koin.android.ext.android.inject

const val IS_CHANGED_SELECTED_ITEMS = "IS_CHANGED_SELECTED_ITEMS"

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private val mBinding by viewBinding(FragmentSettingsBinding::bind)
    private val mAdapter by lazy { SettingsSwapAdapter(mutableListOf()) }

    private val args by navArgs<SettingsFragmentArgs>()

    private val appPreference by inject<AppPreferences>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(mBinding) {
            back.setOnClickListener {
                if (!args.exRates.CurrencyList.isNullOrEmpty()) {
                    val numCodes =
                        mAdapter.getItems().filter { current -> current.isChecked }
                            .map { current -> current.NumCode }
                    val selectedNumCodes = arrayListOf<String>()
                    selectedNumCodes.addAll(numCodes)
                    val numCodeList: List<String> = (appPreference.selectedExRates.split(";"))

                    for (item in numCodeList) {
                        for (numCode in numCodes) {
                            if (item == numCode) {
                                selectedNumCodes.remove(numCode)
                            }
                        }
                    }
                    for (item in args.exRates.CurrencyList!!) {
                        for (numCode in selectedNumCodes) {
                            if (item.NumCode == numCode) {
                                item.isChecked = false
                            }
                        }
                    }
                }
                requireActivity().onBackPressed()
            }

            settingsCheck.setOnClickListener {
                appPreference.exRates =
                    TextUtils.join(";", mAdapter.getItems().map { item -> item.NumCode })
                appPreference.selectedExRates =
                    TextUtils.join(";",
                        mAdapter.getItems().filter { item -> item.isChecked }
                            .map { item -> item.NumCode })
                setNavigationResult(IS_CHANGED_SELECTED_ITEMS, true)
                requireActivity().onBackPressed()
            }

            recyclerView.adapter = mAdapter
            val currencyList = arrayListOf<Currency>()

            args.exRates.CurrencyList?.let { items ->
                if (appPreference.exRates == "") {
                    currencyList.addAll(items)
                } else {
                    val numCodeList: List<String> = (appPreference.exRates.split(";"))

                    if (items.isNotEmpty() && numCodeList.isNotEmpty()) {
                        for (numCode in numCodeList) {
                            for (currency in items) {
                                if (currency.NumCode == numCode) {
                                    currencyList.add(currency)
                                }
                            }
                        }
                    }
                }
                mAdapter.setItems(currencyList)
            }

            val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                0
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    mAdapter.swapItems(
                        viewHolder.adapterPosition,
                        target.adapterPosition
                    )
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                }

            }
            val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
            itemTouchHelper.attachToRecyclerView(recyclerView)
        }
    }
}