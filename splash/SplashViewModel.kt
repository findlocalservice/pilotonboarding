package com.servicefinder.pilotonboarding.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.servicefinder.pilotonboarding.database.RepoProvider
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {

    private var authKeyLoginData:MutableLiveData<String>? = null
    fun getAuthKey(){
    }
}