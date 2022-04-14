package com.servicefinder.pilotonboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.servicefinder.pilotonboarding.form.MainApiService
import com.servicefinder.pilotonboarding.form.MainViewModel
import com.servicefinder.pilotonboarding.login.LoginApiService
import com.servicefinder.pilotonboarding.login.LoginViewModel
import com.servicefinder.pilotonboarding.splash.SplashViewModel

class GlobalViewModelFactory: ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when(modelClass){
            MainViewModel::class.java ->{
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(AppRetrofit.buildApi(MainApiService::class.java)) as T
            }
            LoginViewModel::class.java ->{
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(AppRetrofit.buildApi(LoginApiService::class.java)) as T
            }
            SplashViewModel::class.java ->{
                @Suppress("UNCHECKED_CAST")
                return SplashViewModel() as T
            }
            else -> {
                throw IllegalArgumentException("No ViewModel Found for this modelClass.")
            }
        }
    }
}