package com.servicefinder.pilotonboarding.form

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.servicefinder.pilotonboarding.common.GeneralResponse
import com.servicefinder.pilotonboarding.common.Resource
import com.servicefinder.pilotonboarding.common.ResponseCodes
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

    val submitFormData = MutableLiveData<Resource<GeneralResponse?>>()
    fun submitForm1(submitFormRequestBody: SubmitForm1RequestBody){
        submitFormData.value = Resource.loading(null)
        viewModelScope.launch {
            val response = api.submitForm1Details(submitFormRequestBody)
            if (response.isSuccessful && response.body()?.status?.code == ResponseCodes.SUCCESS) {
                submitFormData.value = Resource.success(response.body())
            }
        }
    }


    fun callEditFormApi(phoneNo: String){
        viewModelScope.launch {
            val response = api.callFormEditApi(phoneNo)
        }
    }
}