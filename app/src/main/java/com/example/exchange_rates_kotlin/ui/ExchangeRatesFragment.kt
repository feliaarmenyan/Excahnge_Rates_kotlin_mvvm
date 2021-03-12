package com.example.exchange_rates_kotlin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.exchange_rates_kotlin.R
import com.example.exchange_rates_kotlin.core.platform.viewbindingdelegate.viewBinding
import com.example.exchange_rates_kotlin.databinding.FragmentExchangeRatesBinding

class ExchangeRatesFragment : Fragment(R.layout.fragment_exchange_rates) {

    private val mBinding by viewBinding(FragmentExchangeRatesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.settings.setOnClickListener {
            findNavController()
                .navigate(R.id.action_ExchangeRatesFragment_to_SettingsFragment)
        }
    }
}