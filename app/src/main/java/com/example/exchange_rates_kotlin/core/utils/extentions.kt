package com.example.exchange_rates_kotlin.core.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.exchange_rates_kotlin.data.local.preference.AppPreferencesImpl
import java.text.SimpleDateFormat
import java.util.*

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.hasNetworkConnection(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities =
            connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork);
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true;
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true;
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true;
                }
            }
        }
    } else {
        try {
            val activeNetworkInfo = connectivityManager?.activeNetworkInfo;
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true;
            }
        } catch (e: Exception) {
        }
    }
    return false;
}

fun getDate(date: Date?): String? {
    date?.let {
        val sdf = SimpleDateFormat("MM.dd.yyyy")
        return sdf.format(date)
    } ?: run {
        return null
    }
}

fun yesterday(): Date? {
    val cal = Calendar.getInstance()
    cal.add(Calendar.DATE, -1)
    return cal.time
}

fun tomorrow(): Date? {
    val cal = Calendar.getInstance()
    cal.add(Calendar.DATE, +1)
    return cal.time
}

fun <T> Fragment.getNavigationResult(key: String) =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.setNavigationResult(key: String, result: T) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}