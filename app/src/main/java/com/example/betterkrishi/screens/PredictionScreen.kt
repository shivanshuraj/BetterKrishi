package com.example.betterkrishi.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PredictionScreen() {
    Column(Modifier.padding(16.dp)) {
        Text(
            text = "PREDICTED DISEASE", fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(text = "lorem ipsum", modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp))
        Text(text = "SYMPTOMS", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(
            text = "lorem ipsum dolor sit amet",
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
        )
        Text(text = "SOLUTIONS", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(
            text = "lorem ipsum dolor sit amet",
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
        )
        Button(
            onClick = { /*TODO*/ }, modifier = Modifier
                .fillMaxWidth(1.0f)
                .padding(16.dp)
        ) {
            Text(text = "Go Back", )
        }
    }
}
