package com.servicefinder.pilotonboarding.form.profile

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
import com.servicefinder.pilotonboarding.common.FileUtils
import com.servicefinder.pilotonboarding.databinding.FragmentProofilePictureBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception
import kotlin.math.PI

class ProfilePictureFragment : Fragment() {
    private var phoneNo: String? = null
    private var binding: FragmentProofilePictureBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProofilePictureBinding.inflate(inflater)
        phoneNo = arguments?.getString(phoneno)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.takePicButton?.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAPTURE_IMAGE_INTENT)
        }

        binding?.uploadPicButton?.setOnClickListener {
            val intent =Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            val chooserIntent = Intent.createChooser(intent, "Select Image")
            startActivityForResult(chooserIntent, PICK_IMAGE)
        }
    }

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
                                val newFile = File(
                                    requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                                    file.name
                                )
                                val outputStream = FileOutputStream(newFile)
                                inputStream.copyTo(outputStream)
                                val bitmap = BitmapFactory.decodeFile(newFile.path)
                                binding?.imageView?.setImageBitmap(bitmap)
                            }
                        }
                    } catch (ex: Exception) {
                        Toast.makeText(context, "picking image crashed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
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