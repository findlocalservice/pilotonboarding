package com.servicefinder.pilotonboarding.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.servicefinder.pilotonboarding.form.MainApplication
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import java.io.File

object ImageCompressor {
    const val uploadSize = 1024 * 200L
    suspend fun compressImage(imageFile: File, maxAllowedSize: Long): File {
        var compressRun = 1
        var file = imageFile
        val bitmap = BitmapFactory.decodeFile(file.path)
        val width = bitmap.width
        val height = bitmap.height
        while (file.length() > maxAllowedSize && compressRun < 5) {
            file = Compressor.compress(MainApplication.getContext(), imageFile) {
                resolution(width / 2, height / 2)
                quality(100 - compressRun * 20)
                format(Bitmap.CompressFormat.JPEG)
                size(maxAllowedSize) // 100 kb
            }
            compressRun++
        }
        bitmap.recycle()
        return file
    }
}