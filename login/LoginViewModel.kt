package com.servicefinder.pilotonboarding.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.servicefinder.pilotonboarding.common.ResponseCodes
import kotlinx.coroutines.launch

class LoginViewModel(val apiService: LoginApiService) : ViewModel(){

    val loginReponseLiveData =  MutableLiveData<String?>()
    fun submitPhoneNo(phone_no:String, password: String){
        loginReponseLiveData?.value
        viewModelScope.launch {
            val response = apiService.submitPhoneNo(phone_no, password)
            if(response.isSuccessful && response.body()?.status?.code==ResponseCodes.SUCCESS){
                loginReponseLiveData?.value = response.body()?.authKey
            }
        }
    }
}