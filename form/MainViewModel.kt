package com.servicefinder.pilotonboarding.form

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.timepicker.MaterialTimePicker
import com.servicefinder.pilotonboarding.common.GeneralResponse
import com.servicefinder.pilotonboarding.common.Resource
import com.servicefinder.pilotonboarding.common.ResponseCodes
import com.servicefinder.pilotonboarding.form.serviceform.ServiceFormRequestBody
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class MainViewModel(val api: MainApiService) : ViewModel() {
    val logoutData = MutableLiveData<Boolean>(false)
    fun logout() {
        viewModelScope.launch {
            try {
                val response = api.logout()
                if (response.isSuccessful && response.body()?.status?.code == ResponseCodes.SUCCESS) {
                    logoutData.value = true
                }else{
                    logoutData.value = false
                }
            }catch (ex: Exception){
                logoutData.value = false
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

    val serviceForLiveData = MutableLiveData<Resource<SubmitFormResponse?>>()
    fun submitServiceForm(serviceForm1RequestBody: ServiceFormRequestBody) {
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


    val phoneNumberAvailableLiveData = MutableLiveData<Resource<Boolean?>>()
    fun checkPhoneNumberIsAvailable(phoneNumber: String?) {
        phoneNumberAvailableLiveData.value = Resource.loading(null)
        viewModelScope.launch {
            phoneNumberAvailableLiveData.value = try {
                val response = api.phoneNumberIsAvailable(phoneNumber)
                if (response.isSuccessful &&
                    response.body()?.status?.code == ResponseCodes.SUCCESS &&
                    response?.body()?.pilotData != null
                ) {
                    Resource.success(true)
                } else {
                    Resource.error("")
                }
            } catch (ex: Exception) {
                Resource.error("")
            }
        }
    }


    val profilePictureLiveData = MutableLiveData<Resource<Boolean>>()
    fun uploadProfilePicture(phone_no: String, file: File) {
        profilePictureLiveData.value = Resource.loading()
        viewModelScope.launch {
            val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
            try {
                val response = api.uploadProfilePicture(phone_no, body)
                if (response.isSuccessful && response.body()?.status?.code == 200) {
                    profilePictureLiveData.value = Resource.success(true)
                } else {
                    profilePictureLiveData.value = Resource.success(false)
                }
            } catch (ex: Exception) {
                profilePictureLiveData.value = Resource.error("")
            }
        }
    }


    val documentUploadLiveData = MutableLiveData<Resource<Boolean>>()
    fun uploadDocument(phone_no: String, doc_name: String, doc_type: String, doc_id: String,  file: File){
        documentUploadLiveData.value = Resource.loading()
        viewModelScope.launch {
            val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("document_image", file.name, requestFile)
            try {
                val response = api.uploadDocuments(phone_no, doc_name, doc_type, doc_id,  body)
                if (response.isSuccessful && response.body()?.status?.code == 200) {
                    documentUploadLiveData.value = Resource.success(true)
                } else {
                    documentUploadLiveData.value = Resource.success(false)
                }
            } catch (ex: Exception) {
                documentUploadLiveData.value = Resource.error("")
            }
        }
    }
}