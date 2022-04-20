package com.servicefinder.pilotonboarding.form

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SubmitForm1RequestBody (
    @SerializedName("user_name") @Expose val name: String?,
    @SerializedName("dob") @Expose val dob: String?,
    @SerializedName("address") @Expose val address: String?,
    @SerializedName("phone_number") @Expose val phoneNumber: String?,
    @SerializedName("gender") @Expose val gender: String?,
    @SerializedName("alternate_phone_number") @Expose val alternatePhoneNo: String?,
    @SerializedName("is_whatsapp_present") @Expose val isWhatsappPresent: Boolean?

)