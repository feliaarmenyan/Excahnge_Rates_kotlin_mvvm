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
import com.example.exchange_rates_kotlin.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private val mBinding by viewBinding(FragmentSettingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.back.setOnClickListener{
            requireActivity().onBackPressed()
        }
    }
}