package com.servicefinder.pilotonboarding.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.servicefinder.pilotonboarding.R
import com.servicefinder.pilotonboarding.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity:AppCompatActivity() {
    private var binding: ActivitySplashBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)

    }
}