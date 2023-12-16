package com.capstone.healthscanapp.ui.app.ui.home_camera
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.capstone.healthscanapp.databinding.ActivityCameraBinding
import com.capstone.healthscanapp.ml.FcModel
import com.capstone.healthscanapp.ui.app.ui.home_camera.IntentCameraActivity.Companion.CAMERA_RESULT
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import timber.log.Timber
import java.io.IOException


class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding

    private var currentImageUri: Uri? = null
    private lateinit var bitmap: Bitmap


    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }


    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()



        val labels = application.assets.open("labels.txt").bufferedReader().readLines()


        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.iconBack.setOnClickListener {
            finish()
        }

        binding.btnCamera.setOnClickListener { startCamera() }
        binding.btnGallery.setOnClickListener { startGallery() }
        binding.btnUpload.setOnClickListener {  }


        binding.btnPrediksi.setOnClickListener {
            if (::bitmap.isInitialized) {
                val tensorImage = TensorImage(DataType.FLOAT32)

                val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
                tensorImage.load(resizedBitmap)

                val model = FcModel.newInstance(this)

                val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
                inputFeature0.loadBuffer(tensorImage.buffer)

                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

                var maxIdx = 0
                outputFeature0.forEachIndexed { index, fl ->
                    if (outputFeature0[maxIdx] < fl) {
                        maxIdx = index
                    }
                }

                binding.edtDescription.text = labels[maxIdx]

                model.close()
            } else {
                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
            }
        }


    }



    private fun startCamera() {
        val intent = Intent(this, IntentCameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_RESULT) {
            currentImageUri = it.data?.getStringExtra(IntentCameraActivity.EXTRA_CAMERA_IMAGE)?.toUri()
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let { uri ->
            Timber.tag("Image URI").d("showImage: %s", uri)
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                binding.imgPreview.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }



    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent, 100)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == 100) {
            val uri = data?.data
            uri?.let {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, it)
                    binding.imgPreview.setImageBitmap(bitmap)
                    currentImageUri = it // Update currentImageUri with the selected image URI
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }


    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}
