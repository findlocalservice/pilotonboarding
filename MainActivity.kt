package com.servicefinder.pilotonboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.servicefinder.pilotonboarding.databinding.ActivityMainBinding
import com.servicefinder.pilotonboarding.formpage.FormAdapter

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var viewModel : MainViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
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
    }
}