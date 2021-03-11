package com.example.exchange_rates_kotlin.core.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.exchange_rates_kotlin.data.local.preference.AppPreferencesImpl
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.text.MessageFormat
import java.text.SimpleDateFormat
import java.util.*


fun Context.displayWidth() = resources.displayMetrics.widthPixels
fun Context.displayHeight() = resources.displayMetrics.heightPixels

fun Context.isServiceRunning(serviceClassName: String): Boolean {
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager

    return activityManager?.getRunningServices(Integer.MAX_VALUE)
        ?.any { it.service.className == serviceClassName } ?: false
}

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

fun getTracksPluralsFormat(count: Int?): String {
    return MessageFormat.format("{0,choice,0#No tracks|1#1 track|1<{0} tracks}", count)
}

fun Date?.getYearDate(): String {
    this?.let {
        val sdf = SimpleDateFormat("yyyy")
        return sdf.format(date)
    } ?: run {
        return ""
    }
}

fun TextView.getDate(date: Date?) {
    date?.let {
        val pref = AppPreferencesImpl(this.context)
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale(pref.languageCode))
        this.text = sdf.format(date)
    } ?: run {
        this.text = ""
    }
}

val Int.pxToDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.dpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun <T> Fragment.getNavigationResult(key: String) =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.setNavigationResult(key: String, result: T) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

internal inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)

fun Context.goWithLink(link: String) {
    try {
        val uri: Uri =
            Uri.parse(link) // missing 'http://' will cause crashed

        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    } catch (e: java.lang.Exception) {
        Timber.e("Activity Exception $e")
    }
}

fun Activity.share(uri: String) {
    try {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND

        intent.putExtra(Intent.EXTRA_TEXT, uri)
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, "Share To:"))
    } catch (e: java.lang.Exception) {
        Timber.e("Activity Exception $e")
    }
}