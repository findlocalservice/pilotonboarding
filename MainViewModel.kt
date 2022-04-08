package com.servicefinder.pilotonboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.servicefinder.pilotonboarding.common.ResponseCodes
import com.servicefinder.pilotonboarding.database.RepoProvider
import kotlinx.coroutines.launch

class MainViewModel(val api: MainApiService): ViewModel() {
    val formData = MutableLiveData<FormData?>()
    fun getFormData(){
        viewModelScope.launch {
            val response = api.getFormDetails()
            if(response.isSuccessful && response.body()?.status?.code==200){
                formData.value = response.body()?.data
            }
        }
    }

    val logoutData = MutableLiveData<Boolean>(false)
    fun logout(token: String){
        viewModelScope.launch {
            val response = api.logout(token)
            if(response.isSuccessful && response.body()?.status?.code == ResponseCodes.SUCCESS){
                logoutData.value = true
            }
        }
    }
}