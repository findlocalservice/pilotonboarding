package com.servicefinder.pilotonboarding.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.servicefinder.pilotonboarding.Status

class LoginResponse (
    @SerializedName("status") @Expose val status: Status?,
    @SerializedName("data") @Expose val data: LoginData?
)

class LoginData(
    @SerializedName("auth_key") @Expose val authKey: String?
)