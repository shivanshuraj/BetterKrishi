package com.example.betterkrishi.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.betterkrishi.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

private const val TAG = "ImagePickerScreen"


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ImagePickerScreen(onImagePicked: (Bitmap) -> String, context: Context) {
    var result by remember { mutableStateOf("...") }
    // Permission launcher

    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            // Handle permission result
        }

    // Launch camera after permission check
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let { result = onImagePicked(it) }
    }


    // Launch gallery
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val bitmap = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, uri)
                    ImageDecoder.decodeBitmap(source)
                }
                result = onImagePicked(bitmap)
                Log.d(TAG, "ImagePickerScreen: $result")
            }
        }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Namaskar!!",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_image),
            contentDescription = null,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Capture an image or select one from gallery",
            style = MaterialTheme.typography.bodyMedium,
        )

        Row {
            // Camera button
            Button(onClick = {
                cameraPermissionState.launchPermissionRequest()
            }) {
                Text("Request Camera Permission")
            }

            // Check permission before opening camera

                ClickableImage(
                    imagePainter = painterResource(id = R.drawable.ic_cam),
                    contentDescription = "Launch camera",
                    onClick = { cameraLauncher.launch(null) }
                )


            Text(
                text = result, modifier = Modifier.padding(16.dp), // Add padding around the text
                fontSize = 20.sp, // Set font size
                fontWeight = FontWeight.Bold
            )// Set font weight)
        }


        // Gallery button and other UI elements...
        // Check and request permission
        LaunchedEffect(key1 = true) {
            cameraPermissionState.launchPermissionRequest()
        }
    }


}

@Composable
fun ClickableImage(
    imagePainter: Painter,
    contentDescription: String?,
    onClick: () -> Unit
) {
    Image(
        painter = imagePainter,
        contentDescription = contentDescription,
        modifier = Modifier.clickable(onClick = onClick)
    )
}
