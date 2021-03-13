package com.example.exchange_rates_kotlin.ui.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.exchange_rates_kotlin.R
import com.example.exchange_rates_kotlin.core.platform.viewbindingdelegate.viewBinding
import com.example.exchange_rates_kotlin.data.remote.model.Currency
import com.example.exchange_rates_kotlin.databinding.FragmentExchangeRatesBinding
import com.example.exchange_rates_kotlin.databinding.FragmentSettingsBinding
import com.example.exchange_rates_kotlin.ui.exchagerates.ExRatesAdapter

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private val mBinding by viewBinding(FragmentSettingsBinding::bind)
    private val mAdapter by lazy { SettingsSwapAdapter(mutableListOf()) }

    private val args by navArgs<SettingsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(mBinding) {
            back.setOnClickListener {
                requireActivity().onBackPressed()
            }

            settingsCheck.setOnClickListener{
                Log.e("my  list ", mAdapter.getItems().toString())
                requireActivity().onBackPressed()
            }

            recyclerView.adapter = mAdapter
            mAdapter.setItems(args.exRates.CurrencyList as List<Currency>)

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