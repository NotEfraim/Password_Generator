package com.itechcom.passwordgenerator.storage.sharedPref

import android.content.Context
import com.itechcom.passwordgenerator.R
import com.itechcom.passwordgenerator.storage.StringKeys
import javax.inject.Inject

class SharedPrefManager @Inject constructor(val context: Context) {

    private val sharedPrefManager = context.getSharedPreferences(context.getString(R.string.sharedPrefName), Context.MODE_PRIVATE)

    fun getString(key : String) = sharedPrefManager.getString(key, "")
    fun setString(key : String, value : String) = sharedPrefManager.edit().putString(key, value).apply()

    fun getBoolean(key: String, value : Boolean? = null) = sharedPrefManager.getBoolean(key, value?:false)
    fun setBoolean(key : String, value : Boolean) = sharedPrefManager.edit().putBoolean(key, value).apply()

    fun getInt(key: String) = sharedPrefManager.getInt(key, 0)
    fun setInt(key : String, value: Int) = sharedPrefManager.edit().putInt(key, value).apply()

    fun getAppPin() = sharedPrefManager.getString(StringKeys.APP_PIN, "")
    fun setAppPin(pin : String) = sharedPrefManager.edit().putString(StringKeys.APP_PIN, pin).apply()
}

