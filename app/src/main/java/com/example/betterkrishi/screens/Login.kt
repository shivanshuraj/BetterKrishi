package com.example.betterkrishi.screens

// Import necessary libraries
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.betterkrishi.ui.theme.BetterGreen

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavController) {
    val state = remember {
        mutableStateOf("")
    }
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        Text(
            text = "Welcome,",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 40.sp),
            modifier = Modifier.padding(start = 16.dp, top = 36.dp)
        )
        Text(
            text = "Log In",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 32.sp, color = BetterGreen),
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "to continue",
            style = TextStyle(fontSize = 26.sp),
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.value,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            onValueChange = { state.value = it },
            label = { Text("Enter your phone no.") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("home") },
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

//@Preview(showBackground = true, showSystemUi = true, heightDp = 400, widthDp = 300)
//@Composable
//fun Previewer() {
//    LoginScreen()
//}

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
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
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
////        OutlinedTextField(
////            value = phoneNumber.value,
////            onValueChange = { phoneNumber.value = phoneNumber },
////            label = { Text(text = "Hello") },
////            keyboardType = KeyboardType.Number,
////            modifier = Modifier.fillMaxWidth()
////        )
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
//                text = stringResource(R.string.phone_number_login),
//                style = MaterialTheme.typography.bodyMedium
//            )
//        }
//
////        if (viewModel.errorMessage.()) {
////            Spacer(modifier = Modifier.height(16.dp))
////            Text(
////                text = viewModel.errorMessage,
////                color = MaterialTheme.colorScheme.error,
////                style = MaterialTheme.typography.labelMedium
////            )
////        }
//    }
//}
//

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
