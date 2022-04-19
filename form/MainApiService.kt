package com.servicefinder.pilotonboarding.form

import com.servicefinder.pilotonboarding.common.GeneralResponse
import com.servicefinder.pilotonboarding.form.serviceform.ServiceFormRequestBody
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface MainApiService {

    @POST("admin/logout")
    suspend fun logout():Response<GeneralResponse>

    @POST("pilot/create_pilot")
    suspend fun submitForm1Details(@Body submitFormRequestBody: SubmitForm1RequestBody): Response<SubmitFormResponse>

    @POST("pilot/create_service_properties")
    suspend fun submitServiceForm(@Body submitServiceForm: ServiceFormRequestBody): Response<SubmitFormResponse>


    @GET("pilot/pilot_data")
    suspend fun phoneNumberIsAvailable(
        @Query("phone_number") phoneNo: String?
    ): Response<PilotDataResponse>

    @Multipart
    @POST("pilot/pilot_profile_image")
    suspend fun uploadProfilePicture(
        @Query("phone_number") phone_no: String, @Part image: MultipartBody.Part
    ): Response<GeneralResponse>

    @Multipart
    @POST("pilot/upload_document_data")
    suspend fun uploadDocuments(
        @Query("phone_number") phone_no: String,
        @Query("doc_name") doc_name: String,
        @Query ("doc_type") doc_type: String,
        @Query ("doc_id") doc_id: String,
        @Part image: MultipartBody.Part
    ): Response<GeneralResponse>
}