package com.example.exchange_rates_kotlin.data.local.preference

import android.content.Context
import com.example.exchange_rates_kotlin.data.local.preference.AppPreferences.Companion.PREF_KEY_EX_RATES
import com.example.exchange_rates_kotlin.data.local.preference.AppPreferences.Companion.PREF_KEY_SELECTED_EX_RATES
import com.example.exchange_rates_kotlin.data.local.preference.AppPreferences.Companion.PREF_NAME


interface AppPreferences {

    var exRates: String
    var selectedExRates: String

    companion object {
        const val PREF_NAME = "ExRatesPrefs"

        const val PREF_KEY_EX_RATES = "PREF_KEY_EX_RATES"
        const val PREF_KEY_SELECTED_EX_RATES = "PREF_KEY_SELECTED_EX_RATES"
    }
}

class AppPreferencesImpl(private val context: Context) : AppPreferences {

    private val mPrefs by lazy {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    override var exRates: String
        get() = mPrefs.getString(PREF_KEY_EX_RATES, "")!!
        set(value) = mPrefs.edit().putString(PREF_KEY_EX_RATES, value).apply()

    override var selectedExRates: String
        get() = mPrefs.getString(PREF_KEY_SELECTED_EX_RATES, "")!!
        set(value) = mPrefs.edit().putString(PREF_KEY_SELECTED_EX_RATES, value).apply()
}