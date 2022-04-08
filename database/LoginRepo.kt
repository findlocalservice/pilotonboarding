package com.servicefinder.pilotonboarding.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

class LoginRepo (val dataBase: AppDataBase){
    private val scope : CoroutineScope = CoroutineScope(Dispatchers.IO)
    fun getAuthKey(): LiveData<String?>{
        val data = MutableLiveData<String?>()
        scope.launch {
            val authKey = dataBase.getLoginTable().getAuthKey()
            data.postValue(authKey)
        }
        return data
    }

    fun setAuthKey(loginData: LoginTable){
        scope.launch {
            dataBase.getLoginTable().insertAuthKey(loginData)
        }
    }

    fun deleteAuthKey(){

    }

}