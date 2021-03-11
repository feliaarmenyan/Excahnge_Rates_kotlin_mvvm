package com.example.exchange_rates_kotlin.data.local.preference

import android.content.Context
import com.example.exchange_rates_kotlin.data.local.preference.AppPreferences.Companion.PREF_KEY_ACCESS_TOKEN
import com.example.exchange_rates_kotlin.data.local.preference.AppPreferences.Companion.PREF_KEY_DEVICE_REGISTERED
import com.example.exchange_rates_kotlin.data.local.preference.AppPreferences.Companion.PREF_KEY_DEVICE_TOKEN
import com.example.exchange_rates_kotlin.data.local.preference.AppPreferences.Companion.PREF_KEY_IS_ALL_FIELDS_CONTAINS
import com.example.exchange_rates_kotlin.data.local.preference.AppPreferences.Companion.PREF_KEY_IS_NOTIFICATIONS_ENABLE
import com.example.exchange_rates_kotlin.data.local.preference.AppPreferences.Companion.PREF_KEY_IS_USER_LOGGED_IN
import com.example.exchange_rates_kotlin.data.local.preference.AppPreferences.Companion.PREF_KEY_LANGUAGE_CODE
import com.example.exchange_rates_kotlin.data.local.preference.AppPreferences.Companion.PREF_KEY_REFRESH_TOKEN
import com.example.exchange_rates_kotlin.data.local.preference.AppPreferences.Companion.PREF_KEY_USER_DEVICE_REGISTERED
import com.example.exchange_rates_kotlin.data.local.preference.AppPreferences.Companion.PREF_KEY_USER_ID
import com.example.exchange_rates_kotlin.data.local.preference.AppPreferences.Companion.PREF_NAME


interface AppPreferences {
    var languageCode: String

    var userId: String
    var isUserLogged: Boolean
    var isAllFieldsContains: Boolean

    var accessToken: String
    var refreshToken: String

    var notificationEnable: Boolean
    var deviceToken: String
    fun clearAllPrefData()


    //new region
    var deviceRegistered: String
    var userDeviceRegistered: String

    companion object {
        const val PREF_NAME = "YouTopPrefs"

        const val PREF_KEY_LANGUAGE_CODE = "PREF_KEY_LANGUAGE_CODE"
        const val PREF_KEY_USER_ID = "PREF_KEY_USER_ID"
        const val PREF_KEY_IS_USER_LOGGED_IN = "PREF_KEY_IS_USER_LOGGED_IN"
        const val PREF_KEY_IS_ALL_FIELDS_CONTAINS = "PREF_KEY_IS_ALL_FIELDS_CONTAINS"

        const val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
        const val PREF_KEY_REFRESH_TOKEN = "PREF_KEY_REFRESH_TOKEN"

        const val PREF_KEY_IS_NOTIFICATIONS_ENABLE = "PREF_KEY_IS_NOTIFICATIONS_ENABLE"
        const val PREF_KEY_DEVICE_TOKEN = "PREF_KEY_DEVICE_TOKEN"

        //new region
        const val PREF_KEY_DEVICE_REGISTERED = "PREF_KEY_DEVICE_REGISTERED"
        const val PREF_KEY_USER_DEVICE_REGISTERED = "PREF_KEY_USER_DEVICE_REGISTERED"
    }
}

class AppPreferencesImpl(private val context: Context) : AppPreferences {

    private val mPrefs by lazy {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    override var languageCode: String
        get() = mPrefs.getString(PREF_KEY_LANGUAGE_CODE, "en")!!
        set(value) = mPrefs.edit().putString(PREF_KEY_LANGUAGE_CODE, value).apply()

    override var userId: String
        get() = mPrefs.getString(PREF_KEY_USER_ID, "")!!
        set(value) = mPrefs.edit().putString(PREF_KEY_USER_ID, value).apply()

    override var isUserLogged: Boolean
        get() = mPrefs.getBoolean(PREF_KEY_IS_USER_LOGGED_IN, false)
        set(value) = mPrefs.edit().putBoolean(PREF_KEY_IS_USER_LOGGED_IN, value).apply()

    override var isAllFieldsContains: Boolean
        get() = mPrefs.getBoolean(PREF_KEY_IS_ALL_FIELDS_CONTAINS, false)
        set(value) = mPrefs.edit().putBoolean(PREF_KEY_IS_ALL_FIELDS_CONTAINS, value).apply()

    override var accessToken: String
        get() = mPrefs.getString(PREF_KEY_ACCESS_TOKEN, "")!!
        set(value) = mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, value).apply()

    override var refreshToken: String
        get() = mPrefs.getString(PREF_KEY_REFRESH_TOKEN, "")!!
        set(value) = mPrefs.edit().putString(PREF_KEY_REFRESH_TOKEN, value).apply()

    override var notificationEnable: Boolean
        get() = mPrefs.getBoolean(PREF_KEY_IS_NOTIFICATIONS_ENABLE, true)
        set(value) = mPrefs.edit().putBoolean(PREF_KEY_IS_NOTIFICATIONS_ENABLE, value).apply()

    override var deviceToken: String
        get() = mPrefs.getString(PREF_KEY_DEVICE_TOKEN, "")!!
        set(value) = mPrefs.edit().putString(PREF_KEY_DEVICE_TOKEN, value).apply()

    override fun clearAllPrefData() {
        val value = mPrefs.getString(PREF_KEY_DEVICE_REGISTERED, "")!!
        mPrefs.edit().clear().apply()
        mPrefs.edit().putString(PREF_KEY_DEVICE_REGISTERED, value).apply()
    }


    //new region
    override var deviceRegistered: String
        get() = mPrefs.getString(PREF_KEY_DEVICE_REGISTERED, "")!!
        set(value) = mPrefs.edit().putString(PREF_KEY_DEVICE_REGISTERED, value).apply()
    override var userDeviceRegistered: String
        get() = mPrefs.getString(PREF_KEY_USER_DEVICE_REGISTERED, "")!!
        set(value) = mPrefs.edit().putString(PREF_KEY_USER_DEVICE_REGISTERED, value).apply()
}