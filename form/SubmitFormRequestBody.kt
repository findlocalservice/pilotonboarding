package com.servicefinder.pilotonboarding.form

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SubmitForm1RequestBody (
    @SerializedName("name") @Expose val name: String?,
    @SerializedName("Age") @Expose val age: String?,
    @SerializedName("address") @Expose val address: String?,
    @SerializedName("phone_no") @Expose val phoneNo: String?,
)