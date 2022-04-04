package com.servicefinder.pilotonboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
}