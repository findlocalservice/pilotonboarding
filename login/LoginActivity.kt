package com.servicefinder.pilotonboarding.login

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.servicefinder.pilotonboarding.GlobalViewModelFactory
import com.servicefinder.pilotonboarding.R
import com.servicefinder.pilotonboarding.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private var binding: ActivityLoginBinding? = null
    private var viewModel: LoginViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        viewModel =
            ViewModelProvider(this, GlobalViewModelFactory()).get(LoginViewModel::class.java)
        binding?.submitButton?.isEnabled = false
        binding?.phoneNo?.addTextChangedListener {
            binding?.submitButton?.isEnabled = it?.length == 10
        }
        binding?.submitButton?.apply {

            setOnClickListener {
                viewModel?.submitPhoneNo(
                    binding?.phoneNo?.text?.trim().toString(),
                    binding?.password?.text?.trim().toString()
                )
            }
        }
    }
}