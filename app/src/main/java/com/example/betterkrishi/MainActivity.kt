package com.example.betterkrishi

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.betterkrishi.ml.RiceDisease
import com.example.betterkrishi.screens.ImagePickerScreen
import com.example.betterkrishi.screens.LoginScreen
import com.example.betterkrishi.screens.MarketScreen
import com.example.betterkrishi.ui.theme.BetterKrishiTheme
import kotlinx.coroutines.delay
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
            BetterKrishiTheme {
                MyApp()
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun MyApp() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "login") {
            composable("login") { LoginScreen(navController) }
            composable("main") { MainScreen(navController) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun SplashScreen(navigateToMainScreen: () -> Unit = {}) {
        var isSplashScreenVisible by remember { mutableStateOf(true) }

        LaunchedEffect(key1 = true) {
            delay(3000)  // Delay for the splash screen
            isSplashScreenVisible = false
        }

        if (isSplashScreenVisible) {
            // Display the splash screen
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.mipmap.ic_launcher),
                    contentDescription = "Logo",
                    Modifier.size(100.dp)
                )
            }
        } else {
            // Navigate to main screen or main app content

        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MyApp()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun MainScreen(navController: NavHostController) {
        val navItems = listOf(
            Screen.Home,
            Screen.News,
            Screen.Market
        )

        // Get the current back stack entry to determine the selected state
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        Scaffold(
            bottomBar = {
                NavigationBar {
                    navItems.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.route) },
                            label = { Text(screen.route) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                // Avoid multiple copies of the same destination when reselecting the same item
                                if (currentRoute != screen.route) {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController,
                startDestination = Screen.Home.route,
                Modifier.padding(innerPadding)
            ) {
                composable(Screen.Home.route) { ImagePickerScreen(applicationContext, ::processImage) }
                composable(Screen.News.route) {  }
                composable(Screen.Market.route) { MarketScreen(navController) }
            }
        }
    }



    sealed class Screen(val route: String, val icon: ImageVector) {
        object Home : Screen("home", Icons.Default.Home)
        object News : Screen("news", Icons.AutoMirrored.Filled.Article)
        object Market : Screen("market", Icons.Default.ShoppingCart)
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


//@Composable
//fun BottomNavigationBar(navController: NavHostController) {
//    val items = listOf(
//        Screen.Home,
//        Screen.Market,
//        Screen.News
//    )
//    NavigationBar {
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntry?.destination?.route
//        items.forEach { screen ->
//            NavigationBarItem(
//                icon = { Icon(Icons.Filled.Home, contentDescription = null) }, // Change icons as needed
//                label = { Text(screen.name) },
//                selected = currentRoute == screen.route,
//                onClick = {
//                    navController.navigate(screen.route) {
//                        // Avoid multiple copies of the same destination when reselecting the same item
//                        popUpTo(navController.graph.findStartDestination().id) {
//                            saveState = true
//                        }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                }
//            )
//        }
//    }
//}
//
