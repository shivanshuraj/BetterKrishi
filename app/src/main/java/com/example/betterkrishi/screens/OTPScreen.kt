package com.example.betterkrishi.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun OTPInputFields() {
    val focusRequesters = List(4) { FocusRequester() }
    val otpCodes = remember { List(4) { mutableStateOf("") } }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        otpCodes.forEachIndexed { index, state ->
            OutlinedTextField(
                value = state.value,
                onValueChange = { newValue ->
                    if (newValue.length <= 1) {
                        state.value = newValue
                        if (newValue.length == 1 && index < otpCodes.size - 1) {
                            focusRequesters[index + 1].requestFocus()
                        } else if (index == otpCodes.size - 1) {
                            focusManager.clearFocus()
                        }
                    }
                },
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = if (index == otpCodes.size - 1) ImeAction.Done else ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { if (index < otpCodes.size - 1) focusRequesters[index + 1].requestFocus() },
                    onDone = { focusManager.clearFocus() }
                ),
                modifier = Modifier
                    .size(50.dp)
                    .focusRequester(focusRequesters[index])
                    .background(Color.LightGray)
            )
            if (index < otpCodes.size - 1) Spacer(modifier = Modifier.width(8.dp))
        }
    }

    // Request initial focus to the first OTP field
    LaunchedEffect(true) {
        focusRequesters[0].requestFocus()
    }
}


@Composable
fun SubmitOTPButton(onSubmit: () -> Unit) {
    Button(onClick = { onSubmit() }) {
        Text("Verify OTP", )
    }
}
@Composable
fun OTPScreen(navController: NavController) {
    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxHeight(1f)
        .fillMaxWidth(1f), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Enter the OTP sent to your number", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium), modifier = Modifier.padding(top = 86.dp))
        Spacer(modifier = Modifier.height(16.dp))
        OTPInputFields()
        Spacer(modifier = Modifier.height(16.dp))
        SubmitOTPButton(onSubmit = {
            // Handle OTP submission
            // Verify OTP here or navigate to next screen
            navController.navigate("home")
        })
    }
}
