package com.servicefinder.pilotonboarding.common

import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
        SharedPreferences.getString("Auth_Key")?.let { request.header("Authorization", it) }
        return chain.proceed(request.build())
    }
}