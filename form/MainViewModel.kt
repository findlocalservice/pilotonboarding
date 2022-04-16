package com.servicefinder.pilotonboarding.form

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.timepicker.MaterialTimePicker
import com.servicefinder.pilotonboarding.common.GeneralResponse
import com.servicefinder.pilotonboarding.common.Resource
import com.servicefinder.pilotonboarding.common.ResponseCodes
import com.servicefinder.pilotonboarding.form.serviceform.ServiceFormRequestBody
import kotlinx.coroutines.launch

class MainViewModel(val api: MainApiService) : ViewModel() {
    val formData = MutableLiveData<FormData?>()
    fun getFormData() {
        viewModelScope.launch {
            val response = api.getFormDetails()
            if (response.isSuccessful && response.body()?.status?.code == 200) {
                formData.value = response.body()?.data
            }
        }
    }

    val logoutData = MutableLiveData<Boolean>(false)
    fun logout(token: String) {
        viewModelScope.launch {
            val response = api.logout(token)
            if (response.isSuccessful && response.body()?.status?.code == ResponseCodes.SUCCESS) {
                logoutData.value = true
            }
        }
    }

    val submitFormData = MutableLiveData<Resource<SubmitFormResponse?>>()
    fun submitForm1(submitFormRequestBody: SubmitForm1RequestBody) {
        submitFormData.value = Resource.loading(null)
        viewModelScope.launch {
            submitFormData.value = try {
                val response = api.submitForm1Details(submitFormRequestBody)
                if (response.isSuccessful && response.body()?.status?.code == ResponseCodes.SUCCESS) {
                    Resource.success(response.body())
                } else {
                    Resource.error("")
                }
            } catch (ex: Exception) {
                Resource.error("")
            }
        }
    }

    val serviceForLiveData= MutableLiveData<Resource<SubmitFormResponse?>>()
    fun submitServiceForm(serviceForm1RequestBody: ServiceFormRequestBody){
        serviceForLiveData.value = Resource.loading(null)
        viewModelScope.launch {
            serviceForLiveData.value = try {
                val response = api.submitServiceForm(serviceForm1RequestBody)
                if (response.isSuccessful && response.body()?.status?.code == ResponseCodes.SUCCESS) {
                    Resource.success(response.body())
                } else {
                    Resource.error("")
                }
            } catch (ex: Exception) {
                Resource.error("")
            }
        }
    }


    fun callEditFormApi(phoneNo: String) {
        viewModelScope.launch {
            val response = api.callFormEditApi(phoneNo)
        }
    }
}