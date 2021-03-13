package com.example.exchange_rates_kotlin.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.exchange_rates_kotlin.R
import com.example.exchange_rates_kotlin.core.platform.viewbindingdelegate.viewBinding
import com.example.exchange_rates_kotlin.databinding.FragmentExchangeRatesBinding
import com.example.exchange_rates_kotlin.databinding.FragmentSettingsBinding
import com.example.exchange_rates_kotlin.ui.exchagerates.ExRatesAdapter

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private val mBinding by viewBinding(FragmentSettingsBinding::bind)
    private val mAdapter by lazy { SettingsAdapter() }

    private val args by navArgs<SettingsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(mBinding) {
            back.setOnClickListener {
                requireActivity().onBackPressed()
            }

            recyclerView.adapter = mAdapter

            mAdapter.submitList(args.exRates.CurrencyList)
        }
    }
}