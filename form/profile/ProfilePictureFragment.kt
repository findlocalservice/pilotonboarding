package com.servicefinder.pilotonboarding.form.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.servicefinder.pilotonboarding.GlobalViewModelFactory
import com.servicefinder.pilotonboarding.R
import com.servicefinder.pilotonboarding.common.*
import com.servicefinder.pilotonboarding.databinding.FragmentProofilePictureBinding
import com.servicefinder.pilotonboarding.documents.DocumentUploadFragment
import com.servicefinder.pilotonboarding.form.MainViewModel
import com.servicefinder.pilotonboarding.form.StepsFragment
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ProfilePictureFragment : Fragment() {
    private var phoneNo: String? = null
    private var binding: FragmentProofilePictureBinding? = null
    private var viewModel: MainViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProofilePictureBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, GlobalViewModelFactory()).get(MainViewModel::class.java)
        phoneNo = arguments?.getString(phoneno)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.uploadPicButton?.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            val chooserIntent = Intent.createChooser(intent, "Select Image")
            startActivityForResult(chooserIntent, PICK_IMAGE)
        }


        viewModel?.profilePictureLiveData?.observe(viewLifecycleOwner){
            when(it.status){
                Resource.Status.SUCCESS ->{
                    binding?.progressBar?.visibility = View.GONE
                    if(it.data == true){
                        gotoNextFragment()
                    }else{
                        Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show()
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
        binding?.submit?.setOnClickListener {
            when {
                phoneNo == null -> {
                    Toast.makeText(context, "phone no is null", Toast.LENGTH_SHORT).show()
                }
                photoFile == null -> {
                    Toast.makeText(context, "phoneFile is null", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    viewModel?.uploadProfilePicture(phoneNo!!, photoFile!!)
                }
            }
        }
    }


    private var photoFile: File? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CAPTURE_IMAGE_INTENT -> {
                if (resultCode == Activity.RESULT_OK) {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    binding?.imageView?.apply {
                        visibility = View.VISIBLE
                        setImageBitmap(bitmap)
                    }
                }
            }
            PICK_IMAGE -> {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        val uri = data?.data
                        if (uri != null) {
                            val filePath1 = arrayOf(MediaStore.Images.Media.DATA)
                            val cursor =
                                context?.contentResolver?.query(uri, filePath1, null, null, null)
                            cursor?.moveToFirst();
                            cursor?.close()
                            val filePath = context?.let { FileUtils.getPath(context, uri) } ?: ""
                            val file = File(filePath)
                            val resolver = requireContext().contentResolver
                            val readOnlyMode = "r"
                            if (!file.exists()) {
                                Toast.makeText(context, "file doesnt exist", Toast.LENGTH_SHORT)
                                    .show()
                                return
                            }
                            resolver.openFileDescriptor(uri, readOnlyMode).use { pfd ->
                                val inputStream = FileInputStream(pfd?.fileDescriptor)
                                var newFile = File(
                                    requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                                    file.name
                                )
                                val outputStream = FileOutputStream(newFile)
                                inputStream.copyTo(outputStream)
                                if(newFile.length() > ImageCompressor.uploadSize){
                                    runBlocking {
                                        try{
                                            newFile = ImageCompressor.compressImage(newFile, ImageCompressor.uploadSize)
                                        }catch (ex: Exception){
                                            Log.i("PROFILEPICTUREFRAGMENT", "IMAGE CRASHED")
                                        }
                                    }
                                }
                                photoFile = newFile
                                val bitmap = BitmapFactory.decodeFile(newFile.path)
                                binding?.imageView?.setImageBitmap(bitmap)
                                binding?.imageView?.visibility = View.VISIBLE
                            }
                        }
                    } catch (ex: Exception) {
                        Toast.makeText(context, "picking image crashed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun gotoNextFragment() {
        val fragment = DocumentUploadFragment.newInstance(phoneNo!!)
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, fragment::class.java.simpleName).commitNow()

    }


    companion object {
        const val PICK_IMAGE = 1001
        const val CAPTURE_IMAGE_INTENT = 1000
        const val phoneno = "phone_no"
        fun newInstance(phoneNo: String): ProfilePictureFragment {
            return ProfilePictureFragment().apply {
                val bundle = Bundle()
                bundle.putString(phoneno, phoneNo)
                arguments = bundle
            }
        }
    }
}