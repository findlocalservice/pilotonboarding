package com.servicefinder.pilotonboarding.form.serviceform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.core.view.size
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.gson.annotations.SerializedName
import com.servicefinder.pilotonboarding.GlobalViewModelFactory
import com.servicefinder.pilotonboarding.R
import com.servicefinder.pilotonboarding.common.Resource
import com.servicefinder.pilotonboarding.databinding.FragmentServiceFormBinding
import com.servicefinder.pilotonboarding.form.Form1Fragment
import com.servicefinder.pilotonboarding.form.MainViewModel
import com.servicefinder.pilotonboarding.form.StepsFragment
import com.servicefinder.pilotonboarding.form.profile.ProfilePictureFragment

class ServiceFormFragment : Fragment() {

    companion object {
        fun newInstance(phoneNo: String): ServiceFormFragment {
            return ServiceFormFragment().apply {
                val bundle = Bundle()
                bundle.putString("phone_no", phoneNo)
                arguments = bundle
            }
        }
    }

    private var phoneNo: String? = null
    private var viewModel: MainViewModel? = null
    var linearLayout: LinearLayout? = null
    private var binding: FragmentServiceFormBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServiceFormBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, GlobalViewModelFactory()).get(MainViewModel::class.java)

        phoneNo = arguments?.getString("phone_no")
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linearLayout = binding?.workTimingContainer
        binding?.workTimingAddButton?.setOnClickListener {
            val view1 = LinearLayout.inflate(context, R.layout.service_worktiming_layout, null)
            val startTimeButton = view1.findViewById<Button>(R.id.start_time_btn)
            val endTimeButton = view1.findViewById<Button>(R.id.end_time_btn)
            val textView1 = view1.findViewById<TextView>(R.id.start_time)
            val textView2 = view1.findViewById<TextView>(R.id.end_time)
            startTimeButton.setOnClickListener {
                val materialTimePicker = MaterialTimePicker.Builder().build()
                materialTimePicker.show(childFragmentManager, "MaterialTimePicker")
                materialTimePicker.addOnPositiveButtonClickListener {
                    val hour = materialTimePicker.hour
                    val min = materialTimePicker.minute
                    textView1.text = "$hour: $min"
                }
            }
            endTimeButton.setOnClickListener {
                val materialTimePicker = MaterialTimePicker.Builder().build()
                materialTimePicker.show(childFragmentManager, "MaterialTimePicker")
                materialTimePicker.addOnPositiveButtonClickListener {
                    val hour = materialTimePicker.hour
                    val min = materialTimePicker.minute
                    textView2.text = "$hour: $min"
                }
            }
            linearLayout?.addView(view1)
        }

        binding?.workTimingDeleteButton?.setOnClickListener {
            val size = linearLayout?.size ?: 0
            if (size > 0) {
                linearLayout?.removeViewAt(size - 1)
            }
        }

        viewModel?.serviceForLiveData?.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    val fragment = ProfilePictureFragment.newInstance(phoneNo!!)
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment::class.java.simpleName)
                        .commitNow()
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {

                }
            }
        }

        binding?.submitButton?.setOnClickListener {
            val requestBody = validateForm()
            if (requestBody != null) {
                viewModel?.submitServiceForm(requestBody)
            }
        }
    }

    private fun validateForm(): ServiceFormRequestBody? {
        val selectedWork = binding?.worktypeA?.selectedItem as String
        val listOfWorkTimings = mutableListOf<SelectedWorkTimings>()
        listOfWorkTimings.clear()
        linearLayout?.forEach {
            val startTime = it.findViewById<TextView>(R.id.start_time).text
            val endTime = it.findViewById<TextView>(R.id.end_time).text
            if (startTime != null && endTime != null) {
                val selectedTimings = SelectedWorkTimings(
                    startTime.toString(), endTime.toString()
                )
                listOfWorkTimings.add(selectedTimings)
            }

        }
        val workExp = binding?.experienceA?.text?.trim().toString()
        val monthlyEarning = binding?.ratesMonthlyA?.text?.trim().toString()
        val dailyEarning = binding?.ratesOneTimeA?.text?.trim().toString()

        val requestBody = ServiceFormRequestBody(
            phonenumber = phoneNo,
            experience = workExp,
            serviceTimings = listOfWorkTimings,
            monthlyRates = monthlyEarning,
            oneTmeRates = dailyEarning,
            serviceType = selectedWork
        )


        return if (selectedWork == null ||
            listOfWorkTimings.isEmpty() ||
            workExp.isNullOrEmpty() ||
            monthlyEarning.isNullOrEmpty() ||
            dailyEarning.isNullOrEmpty()
        ) {
            return null
        } else {
            requestBody
        }
    }
}