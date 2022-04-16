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
        binding?.bottomNavView?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.form -> {
                    gotoForm()
                }
                R.id.edit -> {
                    goToEditForm()
                }
                else ->{

                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun gotoForm() {
        Log.i(TAG, "Starting form1 fragment")
        val fragment = StepsFragment()
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
            .commitNow()
    }

    private fun goToEditForm() {
        Log.i(TAG, "Starting form2 fragment")
        val fragment  = EditFormFragment()
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
            .commitNow()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}