package com.example.betterkrishi

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.ui.graphics.vector.ImageVector

interface Destinations {
    val title: String
    val icon: ImageVector
    val route: String
}

object Home: Destinations{
    override val title="Home"
    override val icon= Icons.Filled.Home
    override val route="home"
}
object News: Destinations{
    override val title="News"
    override val icon= Icons.Filled.Newspaper
    override val route="news"
}

object Market : Destinations {
    override val title = "Market"
    override val icon = Icons.Filled.ShoppingBag
    override val route = "market"
}