package com.servicefinder.pilotonboarding.documents

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.servicefinder.pilotonboarding.GlobalViewModelFactory
import com.servicefinder.pilotonboarding.R
import com.servicefinder.pilotonboarding.common.FileUtils
import com.servicefinder.pilotonboarding.common.ImageCompressor
import com.servicefinder.pilotonboarding.common.Resource
import com.servicefinder.pilotonboarding.databinding.FragmentDocumentBinding
import com.servicefinder.pilotonboarding.form.MainViewModel
import com.servicefinder.pilotonboarding.form.StepsFragment
import com.servicefinder.pilotonboarding.form.profile.ProfilePictureFragment
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class DocumentUploadFragment : Fragment() {
    private var phoneNo: String? = null
    private var binding: FragmentDocumentBinding? = null
    private var viewModel: MainViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDocumentBinding.inflate(inflater)
        phoneNo = arguments?.getString(phoneno)
        viewModel = ViewModelProvider(this, GlobalViewModelFactory()).get(MainViewModel::class.java)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.uploadPhoto?.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            val chooserIntent = Intent.createChooser(intent, "Select Image")
            startActivityForResult(chooserIntent, ProfilePictureFragment.PICK_IMAGE)
        }

        viewModel?.documentUploadLiveData?.observe(viewLifecycleOwner){
            when(it.status){
                Resource.Status.SUCCESS ->{
                    binding?.progressBar?.visibility = View.GONE

                    if(it.data == true){
                        Toast.makeText(context, "Document submitted successfully", Toast.LENGTH_SHORT).show()
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
            if (isReadyToSubmitButton()) {
                viewModel?.uploadDocument(
                    phoneNo!!,
                    binding?.documentName?.selectedItem as String,
                    "${binding?.documentName?.selectedItem}_${binding?.documentType?.selectedItem}.jpg" as String,
                    binding?.documentId?.text?.toString()!!,
                    photoFile!!
                )
            } else {
                Toast.makeText(context, "Full details are not filled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isReadyToSubmitButton(): Boolean {
        if (phoneNo == null
            || binding?.documentName?.selectedItem == null
            || binding?.documentType?.selectedItem == null
            || photoFile == null
        ) {
            return false
        }
        return true
    }

    private var photoFile: File? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ProfilePictureFragment.PICK_IMAGE -> {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        val uri = data?.data
                        if (uri != null) {
                            val filePath1 = arrayOf(MediaStore.Images.Media.DATA)
                            val cursor =
                                context?.contentResolver?.query(uri, filePath1, null, null, null)
                            cursor?.moveToFirst();
                            val columnIndex: Int? = cursor?.getColumnIndex(filePath1.get(0))
                            val picturePath: String? = columnIndex?.let { cursor?.getString(it) }
                            cursor?.close()
                            val thumbnail = BitmapFactory.decodeFile(picturePath)
                            binding?.imageview?.setImageBitmap(thumbnail)
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
                                           newFile =  ImageCompressor.compressImage(newFile, ImageCompressor.uploadSize)
                                        }catch (ex: Exception){

                                        }
                                    }
                                }
                                photoFile = newFile
                                val bitmap = BitmapFactory.decodeFile(newFile.path)
                                binding?.imageview?.setImageBitmap(bitmap)
                                binding?.imageview?.visibility = View.VISIBLE
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
        fun newInstance(phone_no: String): DocumentUploadFragment {
            return DocumentUploadFragment().apply {
                val bundle = Bundle()
                bundle.putString(phoneno, phone_no)
                arguments = bundle
            }
        }
    }

}