//package com.example.betterkrishi.screens
//
//// Import necessary libraries
//import android.content.Context
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.*
//import androidx.compose.material3.Button
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.runtime.saveable.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.example.betterkrishi.R
//
//@Composable
//fun PhoneNumberLoginScreen(
//    viewModel: PhoneNumberLoginViewModel,
//    onLoginSuccess: () -> Unit,
//) {
//    val context = LocalContext.current
//    val phoneNumber = rememberSaveable { mutableStateOf("") }
//    val isLoginEnabled = remember { phoneNumber.value.isNotEmpty() }
//
//    Column(
//        modifier = Modifier.fillMaxSize().padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = stringResource(id = R.string.phone_number_login),
//            style = MaterialTheme.typography.bodyLarge,
//            textAlign = TextAlign.Center
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = phoneNumber.value,
//            onValueChange = { phoneNumber.value = it },
//            label = { Text(stringResource(id = R.string.phone_number)) },
//            keyboardType = KeyboardType.Number,
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = {
//                viewModel.login(context, phoneNumber.value) {
//                    onLoginSuccess()
//                }
//            },
//            enabled = isLoginEnabled,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(
//                text = stringResource(id = R.string.login),
//                style = MaterialTheme.typography.button
//            )
//        }
//
//        if (viewModel.errorMessage.isNotEmpty()) {
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = viewModel.errorMessage,
//                color = MaterialTheme.colors.error,
//                style = MaterialTheme.typography.caption
//            )
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PhoneNumberLoginScreenPreview() {
//    PhoneNumberLoginScreen(
//        viewModel = PhoneNumberLoginViewModel(),
//        onLoginSuccess = {}
//    )
//}
//
//// Define your PhoneNumberLoginViewModel with logic for phone number validation,
//// authentication using the phone number, and error handling.
//class PhoneNumberLoginViewModel {
//    val errorMessage = mutableStateOf("")
//
//    fun login(context: Context, phoneNumber: String, onLoginSuccess: () -> Unit) {
//        // Implement your authentication logic here, e.g., using SMS verification.
//        // If successful, clear the error message and call onLoginSuccess.
//        // Otherwise, set errorMessage with an appropriate message.
//    }
//}
