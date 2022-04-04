package com.servicefinder.pilotonboarding

import android.app.Application
import android.content.Context

class MainApplication: Application() {

    private var context: Context? = null
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        initializeFirebase()
        initializeApiBuilder()
    }

    private fun initializeApiBuilder(){
    }

    private fun initializeFirebase(){

    }

    companion object{
        fun getAppContext(): Context?{
            return
        }
    }
}