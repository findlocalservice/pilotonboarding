package com.servicefinder.pilotonboarding.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.servicefinder.pilotonboarding.form.Status

class LoginResponse (
    @SerializedName("status") @Expose val status: Status?,
    @SerializedName("auth_key") @Expose val authKey: String?
)
