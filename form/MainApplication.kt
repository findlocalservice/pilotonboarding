package com.servicefinder.pilotonboarding.form

import android.app.Application
import android.content.Context
import com.servicefinder.pilotonboarding.AppRetrofit
import com.servicefinder.pilotonboarding.common.SharedPreferences

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferences.initialize(this)
        initializeFirebase()
        initializeApiBuilder()
    }

    private fun initializeApiBuilder(){
        AppRetrofit.createRetrofitAdapterInstance(this)
    }

    private fun initializeFirebase(){

    }
}