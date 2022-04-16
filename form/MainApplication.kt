package com.servicefinder.pilotonboarding.form

import android.app.Application
import android.content.Context
import com.servicefinder.pilotonboarding.AppRetrofit
import com.servicefinder.pilotonboarding.common.SharedPreferences

class MainApplication: Application() {


    override fun onCreate() {
        super.onCreate()
        context = this
        SharedPreferences.initialize(this)
        initializeFirebase()
        initializeApiBuilder()
    }

    companion object{
        @JvmStatic
        private lateinit var context: Context
        @JvmStatic
        fun getContext(): Context{
           return context
        }
    }

    private fun initializeApiBuilder(){
        AppRetrofit.createRetrofitAdapterInstance(this)
    }

    private fun initializeFirebase(){

    }
}