package com.servicefinder.pilotonboarding.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.servicefinder.pilotonboarding.GlobalViewModelFactory
import com.servicefinder.pilotonboarding.R
import com.servicefinder.pilotonboarding.common.Resource
import com.servicefinder.pilotonboarding.common.SharedPreferences
import com.servicefinder.pilotonboarding.databinding.FragmentEditformBinding
import com.servicefinder.pilotonboarding.documents.DocumentUploadFragment
import com.servicefinder.pilotonboarding.form.profile.ProfilePictureFragment
import com.servicefinder.pilotonboarding.form.serviceform.ServiceFormFragment

class EditFormFragment : Fragment() {
    private var binding: FragmentEditformBinding? = null
    private var viewModel: MainViewModel? = null

    private var state: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditformBinding.inflate(layoutInflater)
        state = arguments?.getString(state_name)
        viewModel = ViewModelProvider(this, GlobalViewModelFactory()).get(MainViewModel::class.java)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.phoneNo?.addTextChangedListener {
            binding?.submitButton?.isEnabled = binding?.phoneNo?.text?.trim()?.length ?: 0 == 10
        }

        viewModel?.phoneNumberAvailableLiveData?.observe(viewLifecycleOwner){
            when(it.status){
                Resource.Status.SUCCESS ->{
                    binding?.progressBar?.visibility = View.GONE
                    if(it.data==true){
                        SharedPreferences.addString(SharedPreferences.phone_no, binding?.phoneNo?.text?.trim()?.toString())
                        goToStep()
                    }else{
                        state = FragmentStates.start_onboarding.name
                        Toast.makeText(context, "Phone number not registered", Toast.LENGTH_SHORT).show()
                        goToStep()
                    }
                }
                Resource.Status.ERROR ->{
                    binding?.progressBar?.visibility = View.GONE
                    Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING ->{
                    binding?.progressBar?.visibility = View.VISIBLE
                }
            }
        }
        binding?.submitButton?.setOnClickListener {
            if (binding?.phoneNo?.text?.trim()?.length ?: 0 == 10) {
                viewModel?.checkPhoneNumberIsAvailable(binding?.phoneNo?.text?.trim().toString())
            } else {
                Toast.makeText(context, "Enter valid number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToStep(){
        when(state){
            FragmentStates.start_onboarding.name ->{
                val fragment = Form1Fragment()
                gotoFragment(fragment)
            }
            FragmentStates.service_page.name ->{
                val fragment = ServiceFormFragment.newInstance(binding?.phoneNo?.text?.trim().toString())
                gotoFragment(fragment)
            }

            FragmentStates.profile_pic_page.name ->{
                val fragment = ProfilePictureFragment.newInstance(binding?.phoneNo?.text?.trim().toString())
                gotoFragment(fragment)
            }
            FragmentStates.documents_page.name ->{
                val fragment = DocumentUploadFragment.newInstance(binding?.phoneNo?.text?.trim().toString())
                gotoFragment(fragment)
            }
            else ->{
                val fragment =  Form1Fragment()
                gotoFragment(fragment)
            }
        }
    }

    private fun gotoFragment(fragment: Fragment){
        parentFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment::class.java.simpleName).commitNow()
    }

    companion object{
        const val state_name = "State_name"
        fun newInstance(step: String): EditFormFragment{
            return EditFormFragment().apply {
                val bundle = Bundle()
                bundle.putString(state_name, step)
                arguments = bundle
            }

        }
    }
}