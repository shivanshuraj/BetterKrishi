package com.example.betterkrishi.screens

// Import necessary libraries
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.betterkrishi.R
import com.example.betterkrishi.ui.theme.Green300

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavController) {
    var input_ph_no by remember {
        mutableStateOf("")
    }
    var state by remember {
        mutableStateOf("")
    }
    var buttonClickedStatus by remember {
        mutableIntStateOf(0)
    }
    var otpValue by remember {
        mutableStateOf("")
    }
    Column(Modifier.padding(16.dp)) {
        Image(
            painter = painterResource(id = R.drawable.group_2__1_),
            contentDescription = "app logo",
            Modifier.size(48.dp, 48.dp)
        )

        if (buttonClickedStatus==0) {
            Text(
                "Enter your phone number", Modifier.padding(top = 20.dp),
                style = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.SemiBold)
            )
            TextField(
                value = state,
                onValueChange = {
                    if (it.length <= 10) {
                        state = it
                    }
                },
                modifier =
                Modifier
                    .fillMaxWidth(1f)
                    .padding(top = 20.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
            )
        } else if(buttonClickedStatus==1) {
            Text(
                "Enter the OTP sent to $input_ph_no", Modifier.padding(top = 20.dp, bottom = 20.dp),
                style = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.SemiBold)
            )
            OtpTextField(otpText = otpValue, otpCount = 4) { value, otpInputFilled ->
                otpValue = value
            }
        }

        Spacer(Modifier.weight(1f))
        Button(
            onClick = {
                buttonClickedStatus++
                input_ph_no = state

                if(buttonClickedStatus==2){
                    navController.navigate("home")
                }
            },
            Modifier
                .fillMaxWidth(1f)
                .size(60.dp)
                .padding(0.dp),
            shape = RectangleShape,
        ) {
            Text(text = "CONTINUE")
        }
    }

}

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String, Boolean) -> Unit
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }

    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = Modifier
            .width(40.dp)
            .border(
                1.dp, when {
                    isFocused -> Green300
                    else -> Green300
                }, RoundedCornerShape(4.dp)
            )
            .padding(2.dp),
        text = char,
        style = MaterialTheme.typography.bodyMedium,
        color = if (isFocused) {
            Green300
        } else {
            Green300
        },
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun loginprev() {
    LoginScreen(navController = rememberNavController())
}