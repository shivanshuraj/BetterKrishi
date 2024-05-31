package com.example.betterkrishi.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.betterkrishi.Product
import com.example.betterkrishi.R
import com.example.betterkrishi.ui.theme.BetterGreen


@Composable
fun ProductCard(product: Product, onProductClick: (Product) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProductClick(product) },

        ) {
        Column {
            Image(
                painter = rememberImagePainter(product.image),
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
            Text(product.name, modifier = Modifier.padding(8.dp))
            Text("Price: $${product.price}", modifier = Modifier.padding(8.dp))
        }
    }
}

val sampleProducts = listOf(
    Product(
        1,
        "Pesticide A",
        "Effective against a variety of bugs",
        R.drawable.pesticide_placeholder,
        2.0
    ),
    Product(2, "Fertilizer B", "Promotes plant growth", R.drawable.pesticide_placeholder, 230.0),
    Product(
        3,
        "Pesticide C",
        "Non-toxic and eco-friendly",
        R.drawable.pesticide_placeholder,
        584.5
    ),
    Product(4, "Fertilizer D", "Rich in nutrients", R.drawable.pesticide_placeholder, 45.0),
    Product(5, "Fertilizer E", "Rich in nutrients", R.drawable.pesticide_placeholder, 45.0),
    Product(6, "Fertilizer F", "Rich in nutrients", R.drawable.pesticide_placeholder, 45.0),
    Product(7, "Fertilizer G", "Rich in nutrients", R.drawable.pesticide_placeholder, 45.0),
    Product(8, "Fertilizer H", "Rich in nutrients", R.drawable.pesticide_placeholder, 45.0),
    Product(9, "Fertilizer I", "Rich in nutrients", R.drawable.pesticide_placeholder, 45.0),
    Product(10, "Fertilizer J", "Rich in nutrients", R.drawable.pesticide_placeholder, 45.0),
    Product(11, "Fertilizer K", "Rich in nutrients", R.drawable.pesticide_placeholder, 45.0),
    Product(12, "Fertilizer L", "Rich in nutrients", R.drawable.pesticide_placeholder, 45.0),
    Product(13, "Fertilizer M", "Rich in nutrients", R.drawable.pesticide_placeholder, 45.0),
)

@Composable
fun ProductGrid(products: List<Product>, onProductClick: (Product) -> Unit) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.padding(top = 48.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductCard(product, onProductClick)
        }
    }

}

@Composable
fun ProductDetails(product: Product) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = product.name, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = product.description, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun MarketScreen(navController: NavController) {
    Text(
        text = "Marketplace",
        style = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold, color = BetterGreen),
        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
    )
    ProductGrid(products = sampleProducts) { product ->
//        navController.navigate("productDetails/${product.id}")
    }
}