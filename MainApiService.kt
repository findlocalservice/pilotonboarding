package com.servicefinder.pilotonboarding

import com.servicefinder.pilotonboarding.common.GeneralResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface MainApiService {
    @GET("")
    suspend fun getFormDetails(): Response<OnboardingFormData>

    @POST("admin/logout")
    suspend fun logout(@Header("Authorization") token : String):Response<GeneralResponse>
}