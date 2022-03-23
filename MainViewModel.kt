package com.servicefinder.pilotonboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(val api: MainApiService): ViewModel() {
    fun getFormData(){
        viewModelScope.launch {
            val response = api.getFormDetails()
            if(response.isSuccessful && response.body()?.status?.code==200){

            }
        }
    }
}