package com.servicefinder.pilotonboarding.form

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.servicefinder.pilotonboarding.GlobalViewModelFactory
import com.servicefinder.pilotonboarding.R
import com.servicefinder.pilotonboarding.common.SharedPreferences
import com.servicefinder.pilotonboarding.database.LoginTable
import com.servicefinder.pilotonboarding.database.RepoProvider
import com.servicefinder.pilotonboarding.databinding.FragmentStepsBinding
import com.servicefinder.pilotonboarding.documents.DocumentUploadFragment
import com.servicefinder.pilotonboarding.form.profile.ProfilePictureFragment
import com.servicefinder.pilotonboarding.form.serviceform.ServiceFormFragment
import com.servicefinder.pilotonboarding.login.LoginActivity

class StepsFragment : Fragment() {
    private var viewModel: MainViewModel? = null
    private var binding: FragmentStepsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentStepsBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, GlobalViewModelFactory()).get(MainViewModel::class.java)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.step1?.setOnClickListener {
            val fragment = Form1Fragment()
            gotoFragment(fragment)
        }

        binding?.step2?.setOnClickListener {
            val phone_no = SharedPreferences.getString(SharedPreferences.phone_no)
            if (phone_no != null) {
                val fragment = ServiceFormFragment.newInstance(phone_no)
                gotoFragment(fragment)
            } else {
                val editFormFragment =
                    EditFormFragment.newInstance(FragmentStates.service_page.name)
                gotoFragment(editFormFragment)
            }
        }

        binding?.step3?.setOnClickListener {
            val phone_no = SharedPreferences.getString(SharedPreferences.phone_no)
            if (phone_no != null) {
                val fragment = ProfilePictureFragment.newInstance(phone_no)
                gotoFragment(fragment)
            } else {
                val editFormFragment =
                    EditFormFragment.newInstance(FragmentStates.profile_pic_page.name)
                gotoFragment(editFormFragment)
            }
        }

        binding?.step4?.setOnClickListener {
            val phone_no = SharedPreferences.getString(SharedPreferences.phone_no)
            if (phone_no != null) {
                val fragment = DocumentUploadFragment.newInstance(phone_no)
                gotoFragment(fragment)
            } else {
                val editFormFragment =
                    EditFormFragment.newInstance(FragmentStates.documents_page.name)
                gotoFragment(editFormFragment)
            }
        }
        viewModel?.logoutData?.observe(viewLifecycleOwner) {
            if (it) {
                context?.let { it1 -> RepoProvider(it1).loginDataBase()?.clearLoginData() }
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
        binding?.logout?.setOnClickListener {
            viewModel?.logout()
        }
        binding?.clearPhoneNo?.setOnClickListener {
            SharedPreferences.addString(SharedPreferences.phone_no, null)
        }
    }

    private fun gotoFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, fragment::class.java.simpleName).commit()
    }
}