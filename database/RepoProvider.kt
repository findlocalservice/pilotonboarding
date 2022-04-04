package com.servicefinder.pilotonboarding.database

import android.content.Context
import androidx.room.Room

class RepoProvider(context: Context) {
    private var database: AppDataBase = Room.databaseBuilder(
        context.applicationContext,
        AppDataBase::class.java,
        "user_database"
    ).build()


    private var loginTableInstance: LoginRepo? = null
    fun loginDataBase():LoginRepo?{
        loginTableInstance = LoginRepo(database)
        return loginTableInstance
    }
}