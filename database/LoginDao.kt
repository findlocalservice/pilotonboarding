package com.servicefinder.pilotonboarding.database

import androidx.room.*

@Dao
interface LoginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthKey(loginData: LoginTable)

    @Query("SELECT auth_key from LoginTable")
    suspend fun getAuthKey(): String?

    @Query("DELETE FROM LoginTable")
    suspend fun clearLoginData(): Int

    @Delete
    suspend fun deleteAuthKey(loginData: LoginTable)
}