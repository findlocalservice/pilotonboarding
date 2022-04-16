package com.servicefinder.pilotonboarding.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.servicefinder.pilotonboarding.R
import com.servicefinder.pilotonboarding.databinding.FragmentStepsBinding

class StepsFragment : Fragment() {

    private var binding: FragmentStepsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentStepsBinding.inflate(inflater)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.step1?.setOnClickListener {
            val fragment = Form1Fragment()
            gotoFragment(fragment)
        }

        binding?.step2?.setOnClickListener {
            val editFormFragment = EditFormFragment.newInstance(FragmentStates.service_page.name)
            gotoFragment(editFormFragment)
        }

        binding?.step3?.setOnClickListener {
            val editFormFragment =
                EditFormFragment.newInstance(FragmentStates.profile_pic_page.name)
            gotoFragment(editFormFragment)
        }

        binding?.step4?.setOnClickListener {
            val editFormFragment = EditFormFragment.newInstance(FragmentStates.documents_page.name)
            gotoFragment(editFormFragment)
        }
        binding?.logout?.setOnClickListener {

        }
    }

    private fun gotoFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, fragment::class.java.simpleName).commit()
    }
}