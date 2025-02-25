package com.example.test1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.affurmation_app.R
import com.example.test1.ui.theme.Test1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Test1Theme {
                AppNavigation()
            }
        }
    }
}

// 🚀 Điều hướng giữa danh sách sản phẩm và màn hình chi tiết
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "product_list") {
        composable("product_list") { ProductListScreen(navController) }
        composable("product_detail/{name}/{price}/{imageRes}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val price = backStackEntry.arguments?.getString("price") ?: ""
            val imageRes = backStackEntry.arguments?.getString("imageRes")?.toIntOrNull() ?: R.drawable.ic_launcher_foreground
            ProductDetailScreen(name, price, imageRes, navController)
        }
    }
}

// 📌 Màn hình danh sách sản phẩm
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(navController: NavController) {
    val productList = listOf(
        Product("Laptop", "1000", "High-performance laptop", 4.5, R.drawable.img),
        Product("Smartphone", "800", "Latest smartphone with best camera", 4.2, R.drawable.img2),

        Product("Headphones", "150", "Noise-canceling headphones", 4.8, R.drawable.img3) ,
        Product("Laptop", "1000", "High-performance laptop", 4.5, R.drawable.img)

    )




    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Arrivals", fontWeight = FontWeight.Bold, fontSize = 20.sp) }
            )
        },
        bottomBar = { BottomNavBar(navController) } // Thêm thanh điều hướng đúng cách
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color(0xFFF5F5F5))
        ) {
            items(productList) { product ->
                ProductItem(product, navController)
            }
        }
    }

}
@Composable
fun BottomNavBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(painterResource(id = R.drawable.ic_home), contentDescription = "Home") },
            selected = false,
            onClick = { /* Xử lý điều hướng */ }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(id = R.drawable.ic_cart), contentDescription = "Cart") },
            selected = false,
            onClick = { /* Xử lý điều hướng */ }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(id = R.drawable.ic_profile), contentDescription = "Profile") },
            selected = false,
            onClick = { /* Xử lý điều hướng */ }
        )
    }
}

// 📌 Hiển thị một sản phẩm
@Composable
fun ProductItem(product: Product, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                navController.navigate("product_detail/${product.name}/${product.price}/${product.imageRes}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        navController.navigate("product_detail/${product.name}/${product.price}/${product.imageRes}")
                    }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(product.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = product.description, fontSize = 14.sp, color = Color.Gray)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "Star",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(text = product.rating.toString(), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFB0E57C), shape = RoundedCornerShape(30.dp))
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "$${product.price}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    IconButton(onClick = { /* Add to cart logic */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.add),
                            contentDescription = "Add to Cart"
                        )
                    }
                }
            }
        }
    }
}

// 📌 Màn hình chi tiết sản phẩm
// 📌 Màn hình chi tiết sản phẩm (Cập nhật để hiển thị hình ảnh)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(name: String, price: String, imageRes: Int, navController: NavController) {
    // Set cứng mô tả sản phẩm và đánh giá
    val productDescription = "Đây là một sản phẩm chất lượng cao với nhiều tính năng đặc biệt."
    val productRating = 4.5 // Đánh giá cố định

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(name, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Hiển thị thông tin sản phẩm
            Text(text = "Tên sản phẩm: $name", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(text = "Giá: $$price", fontSize = 20.sp, color = Color(0xFF4CAF50))
            Spacer(modifier = Modifier.height(8.dp))

            // Hiển thị mô tả sản phẩm (set cứng)
            Text(
                text = "Chi tiết sản phẩm:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = productDescription,
                fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Hiển thị đánh giá (set cứng)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(5) { index ->
                    Icon(
                        painter = painterResource(
                            id = if (index < productRating.toInt()) R.drawable.star else R.drawable.star
                        ),
                        contentDescription = "Star Rating",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = productRating.toString(), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
            ) {
                Text("ADD TO CART", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}


// 📌 Data class cho sản phẩm
data class Product(val name: String, val price: String,val description: String,  // Thêm mô tả sản phẩm
                   val rating: Double,val imageRes: Int, )

