package com.servicefinder.pilotonboarding

import com.servicefinder.pilotonboarding.common.ApiInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object AppRetrofit {
    fun <T> buildApi(clazz : Class<T>): T  {
        val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        okHttpClient.addInterceptor(ApiInterceptor())

        val retrofit = Retrofit.Builder()
            .baseUrl("https://143.110.247.190/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(okHttpClient.build())
            .build()
        return retrofit.create(clazz);
    }
}