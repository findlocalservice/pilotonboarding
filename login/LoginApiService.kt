package com.servicefinder.pilotonboarding.login

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginApiService {
    @GET("")
    suspend fun submitPhoneNo(
        @Query("phone_no") phone_no: String,
        @Query("password") password: String
    ): Response<LoginResponse>
}