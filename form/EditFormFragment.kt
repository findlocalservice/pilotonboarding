package com.servicefinder.pilotonboarding.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.servicefinder.pilotonboarding.GlobalViewModelFactory
import com.servicefinder.pilotonboarding.databinding.FragmentEditformBinding

class EditFormFragment: Fragment() {
    private var binding: FragmentEditformBinding? = null
    private var viewModel: MainViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = FragmentEditformBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, GlobalViewModelFactory()).get(MainViewModel::class.java)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.submitButton?.setOnClickListener {
            if(binding?.phoneNo?.text?.trim()?.length ?: 0 == 10){
                viewModel?.callEditFormApi(binding?.phoneNo?.text?.trim().toString())
            }else{
                Toast.makeText(context, "Enter valid number", Toast.LENGTH_SHORT).show()
            }
        }
    }
}