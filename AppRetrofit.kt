package com.servicefinder.pilotonboarding

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.servicefinder.pilotonboarding.common.ApiInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AppRetrofit {
    private lateinit var retrofitAdapter: Retrofit

    fun createRetrofitAdapterInstance(context: Context){
        val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        okHttpClient.addInterceptor(ApiInterceptor())
            .addInterceptor(ChuckerInterceptor(context))
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)

        retrofitAdapter = Retrofit.Builder()
            .baseUrl("http://143.110.247.190/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(okHttpClient.build())
            .build()
    }

    fun <T> buildApi(clazz : Class<T>): T  {
        return retrofitAdapter.create(clazz);
    }
}