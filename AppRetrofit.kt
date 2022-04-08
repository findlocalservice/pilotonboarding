package com.servicefinder.pilotonboarding

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object AppRetrofit {
    fun <T> buildApi(clazz : Class<T>): T  {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://143.110.247.190/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
        return retrofit.create(clazz);
    }
}