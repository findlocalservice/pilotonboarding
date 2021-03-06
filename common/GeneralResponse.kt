package com.servicefinder.pilotonboarding.common

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.servicefinder.pilotonboarding.form.Status

class GeneralResponse(
    @SerializedName("status") @Expose val status: Status?
)