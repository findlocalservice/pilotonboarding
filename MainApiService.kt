package com.servicefinder.pilotonboarding

import retrofit2.Response
import retrofit2.http.GET

interface MainApiService {
    @GET("")
    suspend fun getFormDetails(): Response<OnboardingFormData>
}