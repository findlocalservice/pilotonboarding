package com.servicefinder.pilotonboarding

import android.app.Application
import android.content.Context

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