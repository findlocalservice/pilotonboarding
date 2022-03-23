package com.servicefinder.pilotonboarding

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OnboardingFormData(
    @SerializedName("status") @Expose val status: Status?,
    @SerializedName("data") @Expose val data: FormData?
)

class FormData(
    @SerializedName("fields")@Expose val fields: List<Fields>
)

class Fields(
    @SerializedName("text") @Expose val text: String?,
    @SerializedName("type") @Expose val type: String?
)

class Status(
    @SerializedName("code") @Expose val code: Int? ,
    @SerializedName("message") @Expose val message:String?
)