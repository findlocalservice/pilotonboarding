package com.servicefinder.pilotonboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GlobalViewModelFactory: ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when(modelClass){
            MainViewModel::class.java ->{
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(AppRetrofit.buildApi(MainApiService::class.java)) as T
            }
            else -> {
                throw IllegalArgumentException("No ViewModel Found for this modelClass.")
            }
        }
    }
}