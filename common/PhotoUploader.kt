package com.servicefinder.pilotonboarding.common

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.servicefinder.pilotonboarding.form.MainApplication
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.io.InputStream
import java.util.concurrent.TimeUnit
import java.util.zip.GZIPInputStream

class PhotoUploader(val originalBitmap: File, val url: String, val listener: ImageUploader) {
    fun startUpload() {
        var inputStream: InputStream? = null
        try {
            var responseCode = 0;
            val requestBody: RequestBody =
                RequestBody.create("image".toMediaTypeOrNull(), originalBitmap)
            val builder = Request.Builder().url(url).put(requestBody)
            val response: Response = getHttpCLient().newCall(builder.build()).execute()
            inputStream = response.body?.byteStream()
            val contentEncoding = response.header("Content-Encoding")
            if (contentEncoding != null && contentEncoding.equals("gzip", ignoreCase = true)) {
                inputStream = GZIPInputStream(inputStream)
            }
            responseCode = response.code
            listener.httpCodeResponse(responseCode)
        } catch (ex: Exception) {
            listener?.httpCodeResponse(0)
        } finally {
            inputStream?.close()
        }
    }

    private fun getHttpCLient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClient.addInterceptor(ChuckerInterceptor(MainApplication.getContext()))
        okHttpClient.addInterceptor(logging)
        okHttpClient.readTimeout(30, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(30, TimeUnit.SECONDS)
        return okHttpClient.build()
    }
}

interface ImageUploader {
    fun httpCodeResponse(code: Int)
}