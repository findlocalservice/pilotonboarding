package com.servicefinder.pilotonboarding.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoginTable(
    @ColumnInfo(name = "auth_key") val authKey: String?
)