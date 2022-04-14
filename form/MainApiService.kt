package com.servicefinder.pilotonboarding.form

import com.servicefinder.pilotonboarding.common.GeneralResponse
import retrofit2.Response
import retrofit2.http.*

interface MainApiService {
    @GET("")
    suspend fun getFormDetails(): Response<OnboardingFormData>

    @POST("admin/logout")
    suspend fun logout(@Header("Authorization") token : String):Response<GeneralResponse>

    @POST("form/submit")
    suspend fun submitForm1Details(@Body submitFormRequestBody: SubmitForm1RequestBody): Response<GeneralResponse>

    @GET("form/edit")
    suspend fun callFormEditApi(
        @Query("phone_no") phoneNo: String
    ): Response<GeneralResponse>

}