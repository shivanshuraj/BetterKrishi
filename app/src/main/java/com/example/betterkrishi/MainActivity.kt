package com.example.betterkrishi

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.betterkrishi.ml.RiceDisease
import com.example.betterkrishi.screens.ImagePickerScreen
import com.example.betterkrishi.screens.LoginScreen
import com.example.betterkrishi.screens.PredictionScreen
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class MainActivity : ComponentActivity() {
    lateinit var labels: List<String>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        labels = application.assets.open("labels.txt").bufferedReader().readLines()

        super.onCreate(savedInstanceState)
        setContent {
            Navigator()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun Navigator() {
        val navController = rememberNavController()
        val navHost = NavHost(navController = navController, startDestination = "login") {
            composable(route = "home") {
                ImagePickerScreen(onImagePicked = { bitmap ->
                    processImage(bitmap)
                }, applicationContext)
            }
            composable(route = "sign up") {
            }
            composable(route = "login") {
                LoginScreen(navController = navController)
            }
            composable (route = "prediction") {
                PredictionScreen()
            }
        }

    }

    companion object {
        private const val MODEL_INPUT_SIZE = 256
        private const val FLOAT_SIZE_BYTES = 4
        private const val PIXEL_SIZE = 3 // Assuming RGB
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun processImage(originalBitmap: Bitmap): String {
        var tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(convertHardwareBitmapToSoftware(originalBitmap))

        var imageProcessor =
            ImageProcessor.Builder().add(ResizeOp(256, 256, ResizeOp.ResizeMethod.BILINEAR)).build()

        tensorImage = imageProcessor.process(tensorImage)
        val model = RiceDisease.newInstance(this)

        // Creates inputs for reference.
        val inputFeature0 =
            TensorBuffer.createFixedSize(intArrayOf(1, 256, 256, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(tensorImage.buffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

        var maxIdx = 0
        outputFeature0.forEachIndexed { index, fl ->
            if (outputFeature0[maxIdx] < fl) {
                maxIdx = index
            }
        }
// Releases model resources if no longer used.
        model.close()
        return labels[maxIdx]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertHardwareBitmapToSoftware(bitmap: Bitmap): Bitmap =
        if (bitmap.config == Bitmap.Config.HARDWARE) {
            bitmap.copy(Bitmap.Config.ARGB_8888, true)
        } else {
            bitmap
        }

}
