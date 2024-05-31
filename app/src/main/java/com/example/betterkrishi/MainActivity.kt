package com.example.betterkrishi

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.betterkrishi.ml.RiceDisease
import com.example.betterkrishi.screens.ImagePickerScreen
import com.example.betterkrishi.screens.MarketScreen
import com.example.betterkrishi.screens.NewsList
import com.example.betterkrishi.screens.OtpSignInScreen
import com.example.betterkrishi.ui.theme.BetterKrishiTheme
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class MainActivity : ComponentActivity() {
    lateinit var labels: List<String>
//    private val authViewModel: AuthViewModel by viewModels()
//    private val apiKey = "c83c7b4800d9406aa5148724ecd3dc43"  // Replace with your actual API key
//    private val country = "us"
//    lateinit var apiService: NewsApiService
//    lateinit var newsViewModel: NewsViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        labels = application.assets.open("labels.txt").bufferedReader().readLines()
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://newsapi.org/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        apiService = retrofit.create(NewsApiService::class.java)
//        val repository = NewsRepository(apiService)
//        val viewModelFactory = NewsViewModelFactory(repository)
//        newsViewModel = ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)
//
//        newsViewModel.fetchTopHeadlines(country, apiKey)
        super.onCreate(savedInstanceState)
        setContent {
            BetterKrishiTheme {
                val context= LocalContext.current
                MyApp(context)
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun MyApp(context: Context) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "login") {
            composable("login") { OtpSignInScreen(navController = navController) }
            composable("main") {
                MainScreen(context) }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun MyBottomNavigation(navController: NavHostController) {
        val destinationsList = listOf(
            Home, News, Market
        )

        var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

        BottomNavigation(backgroundColor = MaterialTheme.colorScheme.primary) {
            destinationsList.forEachIndexed { index, destination ->
                BottomNavigationItem(label = { Text(destination.title) },
                    selected = selectedIndex == index,
                    onClick = {
                        selectedIndex = index
                        navController.navigate(destinationsList[index].route) {
                            popUpTo(Home.route)
                            launchSingleTop = true
                        }

                    },
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = destination.title
                        )
                    })
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun MainScreen(context: Context, newsViewModel: NewsViewModel=viewModel()) {
        val news by newsViewModel.news.observeAsState(emptyList())
        val navController = rememberNavController()
        Scaffold(bottomBar = { MyBottomNavigation(navController) }) {
            Box(modifier = Modifier.padding(it)){
                NavHost(navController = navController, startDestination = "home"){
                    composable("home"){
                        ImagePickerScreen(context = context, ::processImage)
                    }
                    composable("news"){
                        NewsList(news = news)
                    }
                    composable("market"){
                        MarketScreen(navController = navController)
                    }

                }
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
            ImageProcessor.Builder().add(ResizeOp(256, 256, ResizeOp.ResizeMethod.BILINEAR))
                .build()

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

