package com.servicefinder.pilotonboarding.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.servicefinder.pilotonboarding.common.ResponseCodes
import kotlinx.coroutines.launch

class LoginViewModel(val apiService: LoginApiService) : ViewModel(){

    fun submitPhoneNo(phone_no:String, password: String){
        viewModelScope.launch {
            val response = apiService.submitPhoneNo(phone_no, password)
            if(response.isSuccessful && response.body()?.status?.code==ResponseCodes.SUCCESS){

            }
        }
    }
}