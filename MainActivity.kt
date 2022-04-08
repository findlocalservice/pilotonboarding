package com.servicefinder.pilotonboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.servicefinder.pilotonboarding.database.LoginTable
import com.servicefinder.pilotonboarding.database.RepoProvider
import com.servicefinder.pilotonboarding.databinding.ActivityMainBinding
import com.servicefinder.pilotonboarding.formpage.FormAdapter
import com.servicefinder.pilotonboarding.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var viewModel : MainViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            ActivityMainBinding.inflate(LayoutInflater.from(this))
        viewModel = ViewModelProvider(this, GlobalViewModelFactory()).get(MainViewModel::class.java)
        val adapterForm =  FormAdapter()
        binding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterForm
        }
        viewModel?.formData?.observe(this){
            if(it!=null){
                adapterForm.setData(it.fields)
            }
        }

        viewModel?.logoutData?.observe(this){
            if(it==true) {
                Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
                RepoProvider(context = this).loginDataBase()?.setAuthKey(LoginTable(""))
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        binding?.logoutButton?.setOnClickListener {
            RepoProvider(this).loginDataBase()?.getAuthKey()?.observe(this){
                if(!it.isNullOrEmpty()){
                    viewModel?.logout(it)
                }
            }
        }
    }
}