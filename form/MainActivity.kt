package com.servicefinder.pilotonboarding.form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.servicefinder.pilotonboarding.R
import com.servicefinder.pilotonboarding.databinding.ActivityMainBinding
import com.servicefinder.pilotonboarding.form.serviceform.ServiceFormFragment


class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportFragmentManager.beginTransaction().add(R.id.container, StepsFragment())
            .commitAllowingStateLoss()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}