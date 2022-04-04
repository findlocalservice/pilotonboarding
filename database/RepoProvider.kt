package com.servicefinder.pilotonboarding.database

import androidx.room.Room
import com.servicefinder.pilotonboarding.MainApplication

object RepoProvider {
    private var database : AppDataBase = Room.databaseBuilder(MainApplication.getAppContext(), AppDataBase::class.java, "user_database")
}