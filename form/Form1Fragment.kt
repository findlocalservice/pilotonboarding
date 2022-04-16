package com.servicefinder.pilotonboarding.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.DateSelector
import com.google.android.material.datepicker.MaterialDatePicker
import com.servicefinder.pilotonboarding.GlobalViewModelFactory
import com.servicefinder.pilotonboarding.R
import com.servicefinder.pilotonboarding.common.Resource
import com.servicefinder.pilotonboarding.databinding.FragmentFormBinding
import com.servicefinder.pilotonboarding.form.serviceform.ServiceFormFragment
import java.text.SimpleDateFormat
import java.util.*

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
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Toast.makeText(context, "Successfully submitted", Toast.LENGTH_SHORT).show()
                    val fragment = ServiceFormFragment.newInstance(
                        binding?.phoneNoAnswer?.text?.trim().toString()
                    )
                    gotoFragment(fragment)
                }
                Resource.Status.LOADING -> {

                }
                Resource.Status.ERROR -> {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()

                }
            }
        }

        binding?.dobButton?.setOnClickListener {
            val builder = MaterialDatePicker.Builder.datePicker().build()
            builder.show(childFragmentManager, builder.toString())
            builder.addOnPositiveButtonClickListener { date ->
                val formattedDate = formatDateInDDMMYYYYDash(date)
                binding?.dobAnswer?.setText(formattedDate.toString())
            }

        }
        binding?.submitButton?.setOnClickListener {
            if (formValidator()) {
                val submitFormRequestBody = SubmitForm1RequestBody(
                    name = binding?.nameAnswer?.text?.trim().toString(),
                    dob = binding?.dobAnswer?.text?.trim().toString(),
                    address = binding?.addressAnswer?.text?.trim().toString(),
                    phoneNumber = binding?.phoneNoAnswer?.text?.trim().toString(),
                    gender = binding?.phoneNoAnswer?.text?.trim().toString(),
                    alternatePhoneNo = binding?.alternatePhoneNo?.text?.trim().toString()
                )
                viewModel?.submitForm1(submitFormRequestBody)
            } else {
                Toast.makeText(context, "Fill all details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun gotoFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, fragment::class.java.simpleName).commitNow()
    }

    private fun formValidator(): Boolean {
        if (
            binding?.nameAnswer?.text?.trim().isNullOrEmpty() ||
            binding?.phoneNoAnswer?.text?.trim().isNullOrEmpty() ||
            binding?.addressAnswer?.text?.trim().isNullOrEmpty() ||
            binding?.dobAnswer?.text?.trim().isNullOrEmpty() ||
            binding?.genderAns?.text?.trim().isNullOrEmpty()
        ) {
            return false
        }
        return true
    }

    fun formatDateInDDMMYYYYDash(dateInLong: Long): String {
        val df = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        return df.format(Date(dateInLong))
    }

}