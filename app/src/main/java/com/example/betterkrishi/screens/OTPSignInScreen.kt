package com.example.betterkrishi.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.betterkrishi.AuthViewModel
import com.example.betterkrishi.MainActivity
import com.example.betterkrishi.OTPState

@Composable
fun OtpSignInScreen(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    val context = LocalContext.current
    val activity = context as? MainActivity
    var phoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var verificationId by remember { mutableStateOf<String?>(null) }
    val otpState by authViewModel.otpState.observeAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (verificationId != null) {
            TextField(
                value = otp,
                onValueChange = { otp = it },
                label = { Text("OTP") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
//                verificationId?.let { id ->
//                    authViewModel.verifyOtp(id, otp)
//                }
                navController.navigate("main")
            }) {
                Text("Verify OTP")
            }
        } else {
            Button(onClick = {
//                if (activity != null) {
//                    authViewModel.setActivity(activity)
//                }
//                authViewModel.sendOtp(phoneNumber)
                navController.navigate("main")
            }) {
                Text("Send OTP")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        when (val state = otpState) {
            is OTPState.CodeSent -> {
                verificationId = state.verificationId
                Text("Code sent to $phoneNumber")
            }
            is OTPState.VerificationSuccess -> {
                Text("Verification successful!")
                LaunchedEffect(Unit) {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            }
            is OTPState.Error -> {
                Text("Error: ${state.message}", color = MaterialTheme.colorScheme.error)
            }
            else -> {}
        }
    }
}
