package com.example.betterkrishi.screens

// Import necessary libraries
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.betterkrishi.ui.theme.BetterGreen
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavController) {
    val state = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val videoUrl = "android.resource://${context.packageName}/raw/farm"

    Box {
        BackgroundVideoPlayer(
            context = context,
            videoUri = videoUrl,
            modifier = Modifier.matchParentSize()
        )
        // Add your sign-up form elements here
        Box(modifier = Modifier.align(Alignment.Center)) {
            Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
                Text(
                    text = "Welcome,",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(start = 16.dp, top = 36.dp)
                )
                Text(
                    text = "Log In",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(start = 16.dp)
                )
                Text(
                    text = "to continue",
                    style = TextStyle(fontSize = 26.sp, color = Color.White),
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = state.value,
                    textStyle = TextStyle(fontSize = 20.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    onValueChange = {
                        if (it.length <= 10) {
                            state.value = it
                        }
                    },
                    label = { Text("Enter your phone no.") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (state.value.length == 10)
                            navController.navigate("home")
                    },
                    Modifier.padding(start = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BetterGreen,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Login")

                }
            }
        }
    }


}

@Composable
fun BackgroundVideoPlayer(context: Context, videoUri: String, modifier: Modifier = Modifier) {
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(Uri.parse(videoUri))
            setMediaItem(mediaItem)
            repeatMode = ExoPlayer.REPEAT_MODE_ONE // Loop the video
            prepare()
            playWhenReady = true
            volume = 0f // Mute the video
        }
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            StyledPlayerView(ctx).apply {
                player = exoPlayer
                useController = false // Hide the controls
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM // Cover the entire screen
            }
        }
    )
}