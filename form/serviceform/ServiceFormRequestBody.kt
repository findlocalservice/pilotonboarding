package com.servicefinder.pilotonboarding.form.serviceform

import com.google.gson.annotations.SerializedName

class ServiceFormRequestBody(
    @SerializedName("experience") val experience: String?,
    @SerializedName("phone_number") val phonenumber: String?,
    @SerializedName("service_type") val serviceType: String?,
    @SerializedName("service_timings") val serviceTimings: List<SelectedWorkTimings>?,
    @SerializedName("one_time_rates") val oneTmeRates: String?,
    @SerializedName("monthly_rates") val monthlyRates: String?,
    @SerializedName("latitude") val latitude: Double?,
    @SerializedName("longitude") val longitude: Double?
)

data class SelectedWorkTimings(
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String
)