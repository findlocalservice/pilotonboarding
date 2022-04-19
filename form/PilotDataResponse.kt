package com.servicefinder.pilotonboarding.form

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PilotDataResponse(
    @SerializedName("status") @Expose val status: Status?,
    @SerializedName("pilot_data") @Expose val pilotData: PilotData?
)

data class PilotData(
    @SerializedName("phone_number") val phoneNumber: String?
)