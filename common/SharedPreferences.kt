package com.servicefinder.pilotonboarding.common

import android.content.Context
import android.content.SharedPreferences

object SharedPreferences {

    const val phone_no = "phone_no"

    private lateinit var sharedPreferences: SharedPreferences
    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences("application_settings", Context.MODE_PRIVATE)
    }

    @Synchronized
    fun addString(key: String, value: String?){
        if(::sharedPreferences.isInitialized){
            sharedPreferences.edit().putString(key, value).apply()
        }
    }

    @Synchronized
    fun getString(key: String): String?{
        if(::sharedPreferences.isInitialized){
            return sharedPreferences.getString(key, null)
        }
        return null
    }



    @Synchronized
    fun addBoolean(key: String, value: Boolean){
        if(::sharedPreferences.isInitialized){
            sharedPreferences.edit().putBoolean(key, value).apply()
        }
    }
}