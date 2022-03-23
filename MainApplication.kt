package com.servicefinder.pilotonboarding

import android.app.Application

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initializeFirebase()
        initializeApiBuilder()
    }

    private fun initializeApiBuilder(){
    }

    private fun initializeFirebase(){

    }
}