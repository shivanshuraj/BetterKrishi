package com.example.betterkrishi.screens

// Import necessary libraries
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.betterkrishi.R

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavController) {
    var state by remember {
        mutableStateOf("")
    }

    Column(Modifier.padding(16.dp)) {
        Image(
            painter = painterResource(id = R.drawable.group_2__1_),
            contentDescription = "app logo",
            Modifier.size(48.dp, 48.dp)
        )
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
        Spacer(Modifier.weight(1f))
        Button(onClick = { /*TODO*/ }, Modifier.fillMaxWidth(1f).size(60.dp).padding(0.dp), shape = RectangleShape,) {
            Text(text = "CONTINUE")

        }
    }

}
