package com.example.exchange_rates_kotlin.ui.exchagerates

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.exchange_rates_kotlin.R
import com.example.exchange_rates_kotlin.core.platform.viewbindingdelegate.viewBinding
import com.example.exchange_rates_kotlin.core.utils.gone
import com.example.exchange_rates_kotlin.core.utils.invisible
import com.example.exchange_rates_kotlin.core.utils.visible
import com.example.exchange_rates_kotlin.databinding.FragmentExchangeRatesBinding
import com.example.exchange_rates_kotlin.ui.DailyRatesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExchangeRatesFragment : Fragment(R.layout.fragment_exchange_rates) {

    private val mBinding by viewBinding(FragmentExchangeRatesBinding::bind)
    private val mViewModel by viewModel<DailyRatesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.loadItems()
        initViewModels()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.settings.setOnClickListener {
            findNavController()
                .navigate(R.id.action_ExchangeRatesFragment_to_SettingsFragment)
        }
    }

    private fun initViewModels() {
        mViewModel.exRates.observe(this) { items ->
            Log.e("items ",items.toString())
            mBinding.viewError.root.gone(false)
        }

        mViewModel.showError.observe(this){error->
            Log.e( "im error  " , error)
            with(mBinding){
                mBinding.settings.invisible(false)
                viewError.root.visible(false)
            }
        }
    }
}