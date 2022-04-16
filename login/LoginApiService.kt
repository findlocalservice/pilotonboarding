package com.servicefinder.pilotonboarding.login

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginApiService {
    @POST("admin/login")
    suspend fun submitPhoneNo(
        @Query("phone_number") phone_no: String,
        @Query("password") password: String
    ): Response<LoginResponse>
}