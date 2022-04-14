package com.servicefinder.pilotonboarding.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.servicefinder.pilotonboarding.GlobalViewModelFactory
import com.servicefinder.pilotonboarding.form.MainActivity
import com.servicefinder.pilotonboarding.R
import com.servicefinder.pilotonboarding.common.SharedPreferences
import com.servicefinder.pilotonboarding.database.RepoProvider
import com.servicefinder.pilotonboarding.databinding.ActivitySplashBinding
import com.servicefinder.pilotonboarding.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity:AppCompatActivity() {
    private val TAG = "SplashActivity"

    private var binding: ActivitySplashBinding? = null
    private var viewModel: SplashViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)
        viewModel = ViewModelProvider(this, GlobalViewModelFactory()).get(SplashViewModel::class.java)
        RepoProvider(this).loginDataBase()?.getAuthKey()?.observe(this) {
            if(it.isNullOrEmpty()){
                Log.i(TAG, "Starting login activity")
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Log.i(TAG, "Starting main activity")
                SharedPreferences.addString("Auth_Key", it)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}