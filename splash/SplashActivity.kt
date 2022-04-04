package com.servicefinder.pilotonboarding.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.servicefinder.pilotonboarding.GlobalViewModelFactory
import com.servicefinder.pilotonboarding.MainActivity
import com.servicefinder.pilotonboarding.R
import com.servicefinder.pilotonboarding.database.RepoProvider
import com.servicefinder.pilotonboarding.databinding.ActivitySplashBinding
import com.servicefinder.pilotonboarding.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity:AppCompatActivity() {
    private var binding: ActivitySplashBinding? = null
    private var viewModel: SplashViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)
        viewModel = ViewModelProvider(this, GlobalViewModelFactory()).get(SplashViewModel::class.java)
        RepoProvider(this).loginDataBase()?.getAuthKey()?.observe(this) {
            if(it==null){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}