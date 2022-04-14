package com.servicefinder.pilotonboarding.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.servicefinder.pilotonboarding.R
import com.servicefinder.pilotonboarding.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportFragmentManager.beginTransaction().add(R.id.container, Form1Fragment())
            .commitAllowingStateLoss()
        binding?.bottomNavView?.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.form -> {
                    gotoForm()
                }
                R.id.edit -> {
                    goToEditForm()
                }
            }
        }
    }

    private fun gotoForm() {
        Log.i(TAG, "Starting form1 fragment")
        supportFragmentManager.beginTransaction().replace(R.id.container, Form1Fragment())
            .commitAllowingStateLoss()
    }

    private fun goToEditForm() {
        Log.i(TAG, "Starting form2 fragment")
        supportFragmentManager.beginTransaction().replace(R.id.container, EditFormFragment())
            .commitAllowingStateLoss()
    }
}