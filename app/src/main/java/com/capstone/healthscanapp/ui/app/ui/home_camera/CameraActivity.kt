package com.capstone.healthscanapp.ui.app.ui.home_camera
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.google.firebase.Timestamp
import com.capstone.healthscanapp.databinding.ActivityCameraBinding
import com.capstone.healthscanapp.ui.app.ui.home_camera.IntentCameraActivity.Companion.CAMERA_RESULT
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import timber.log.Timber
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel


class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private var currentImageUri: Uri? = null
    private lateinit var bitmap: Bitmap
    private lateinit var interpreter: Interpreter
    private lateinit var currentUserID: String

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

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        currentUserID = FirebaseAuth.getInstance().currentUser?.uid ?: ""


        interpreter = Interpreter(loadModelFile(), Interpreter.Options())

        val labels = application.assets.open("labels.txt").bufferedReader().readLines()
        val nutrition = application.assets.open("nutrition.txt").bufferedReader().readLines()


        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.iconBack.setOnClickListener {
            finish()
        }

        binding.btnCamera.setOnClickListener { startCamera() }
        binding.btnGallery.setOnClickListener { startGallery() }

        binding.btnPrediksi.setOnClickListener {
            if (::bitmap.isInitialized) {
                showLoading(true)
                val tensorImage = TensorImage(DataType.FLOAT32)

                val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
                tensorImage.load(resizedBitmap)

                val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
                inputFeature0.loadBuffer(tensorImage.buffer)

                val outputs = TensorBuffer.createFixedSize(intArrayOf(1, 9), DataType.FLOAT32)
                interpreter.run(inputFeature0.buffer, outputs.buffer.rewind())
                val outputFeature0 = outputs.floatArray

                var maxIdx = 0
                outputFeature0.forEachIndexed { index, fl ->
                    if (outputFeature0[maxIdx] < fl) {
                        maxIdx = index
                    }
                }

                val predictedLabel = labels[maxIdx]
                val predictedNutrition = nutrition[maxIdx]

                binding.edtDescription.text = predictedLabel
                binding.edtNutrion.text = predictedNutrition

                //binding.edtDescription.text = labels[maxIdx]
                val currentTimeStamp = Timestamp.now()

                val dataKonsumsi = hashMapOf(
                    "label" to predictedLabel,
                    "nutrition" to predictedNutrition,
                    "timestamp" to currentTimeStamp
                )
                if (currentUserID.isNotEmpty()) {
                    firestore.collection("users")
                        .document(currentUserID)
                        .collection("riwayat_konsumsi")
                        .add(dataKonsumsi)
                        .addOnSuccessListener {documentReference ->
                            showToast("Data konsumsimu berhasil disimpan")
                            showLoading(false)
                        }
                        .addOnFailureListener { e ->
                            showToast("Gagal menyimpan data konsumsi : ${e.message}")
                            showLoading(false)
                        }
                }


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
    private fun showImage(inputStream: InputStream) {
        try {
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_8888 // Konfigurasi sesuai dengan gambar RGB
            bitmap = BitmapFactory.decodeStream(inputStream, null, options)!!
            binding.imgPreview.setImageBitmap(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    private fun loadModelFile(): MappedByteBuffer {
        val modelFileDescriptor = assets.openFd("fc_model.tflite")
        val inputStream = FileInputStream(modelFileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = modelFileDescriptor.startOffset
        val declaredLength = modelFileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }
    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        launcherGallery.launch(intent)
    }
    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val selectedImage: Uri? = result.data?.data
            selectedImage?.let { uri ->
                try {
                    val inputStream = contentResolver.openInputStream(uri)
                    inputStream?.let {
                        showImage(it)
                        currentImageUri = uri // Update currentImageUri with the selected image URI
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == 100) {
            val uri = data?.data
            uri?.let {
                try {
                    val inputStream = contentResolver.openInputStream(uri)
                    inputStream?.let {
                        showImage(it)
                        currentImageUri = uri
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

