package com.servicefinder.pilotonboarding.form

import com.servicefinder.pilotonboarding.common.GeneralResponse
import com.servicefinder.pilotonboarding.form.serviceform.ServiceFormRequestBody
import retrofit2.Response
import retrofit2.http.*

interface MainApiService {
    @GET("")
    suspend fun getFormDetails(): Response<OnboardingFormData>

    @POST("admin/logout")
    suspend fun logout(@Header("Authorization") token : String):Response<GeneralResponse>

    @POST("pilot/create_pilot")
    suspend fun submitForm1Details(@Body submitFormRequestBody: SubmitForm1RequestBody): Response<SubmitFormResponse>

    @POST("pilot/create_service_properties")
    suspend fun submitServiceForm(@Body submitServiceForm: ServiceFormRequestBody): Response<SubmitFormResponse>


    @GET("form/edit")
    suspend fun callFormEditApi(
        @Query("phone_no") phoneNo: String
    ): Response<GeneralResponse>

}