package com.servicefinder.pilotonboarding.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.servicefinder.pilotonboarding.GlobalViewModelFactory
import com.servicefinder.pilotonboarding.databinding.FragmentFormBinding

class Form1Fragment : Fragment() {
    private var binding: FragmentFormBinding? = null
    private var viewModel: MainViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, GlobalViewModelFactory()).get(MainViewModel::class.java)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel?.submitFormData?.observe(viewLifecycleOwner) {

        }
        binding?.submitButton?.setOnClickListener {
            if (formValidator()) {
                val submitFormRequestBody = SubmitForm1RequestBody(
                    name = binding?.nameAnswer?.text?.trim().toString(),
                    age = binding?.ageAnswer?.text?.trim().toString(),
                    address = binding?.addressAnswer?.text?.trim().toString(),
                    phoneNo = binding?.phoneNoAnswer?.text?.trim().toString()
                )
                viewModel?.submitForm1(submitFormRequestBody)
            }
        }
    }

    private fun formValidator(): Boolean {
        if (
            binding?.nameAnswer?.text?.trim().isNullOrEmpty() ||
            binding?.ageAnswer?.text?.trim().isNullOrEmpty() ||
            binding?.phoneNoAnswer?.text?.trim().isNullOrEmpty() ||
            binding?.addressAnswer?.text?.trim().isNullOrEmpty()
        ) {
            return false
        }
        return true
    }
}