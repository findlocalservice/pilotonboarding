package com.servicefinder.pilotonboarding.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        LoginTable::class
    ],
    version = 1
)

abstract class AppDataBase: RoomDatabase(){
    abstract fun getLoginTable(): LoginDao
}