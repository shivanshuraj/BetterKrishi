package com.example.betterkrishi.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.betterkrishi.Product

@Composable
fun ProductGrid(products: List<Product>, onProductClick: (Product) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
//        items(products) { product ->
//            ProductCard(product, onProductClick)
        }
    }


@Composable
fun ProductCard(product: Product, onProductClick: (Product) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProductClick(product) },

    ) {
        Column {
            Image(
                painter = rememberImagePainter(product.imageUrl),
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