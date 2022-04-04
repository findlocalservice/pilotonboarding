package com.servicefinder.pilotonboarding.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.servicefinder.pilotonboarding.login.LoginData

@Dao
interface LoginDao {
    @Insert
    suspend fun insertAuthKey(loginData: LoginTable)

    @Query("SELECT auth_key from LoginTable")
    suspend fun getAuthKey(): String?
}