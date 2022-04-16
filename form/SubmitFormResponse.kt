package com.servicefinder.pilotonboarding.form

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SubmitFormResponse(
    @SerializedName("status") @Expose val status: Status?,
    @SerializedName("pilot_data") @Expose val data: SubmitFormData
)

class SubmitFormData(
    @SerializedName("on_boarding_state") @Expose val formState: String?
)