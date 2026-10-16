package com.example.firebaseauth.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.firebaseauth.R
import com.example.firebaseauth.navigation.Screen
import com.example.firebaseauth.ui.theme.PizzaDark
import com.example.firebaseauth.ui.theme.PizzaGrey
import com.example.firebaseauth.ui.theme.PizzaRed
import com.example.firebaseauth.ui.theme.PizzaYellow
import com.google.firebase.auth.FirebaseAuth

/**
 * Man hinh sau khi dang nhap thanh cong.
 *
 * Hien email cua nguoi dang nhap va nut Sign Out (goi FirebaseAuth.signOut()
 * roi quay ve man hinh dang nhap).
 */
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val firebaseAuth = remember { FirebaseAuth.getInstance() }

    Column(
            modifier = Modifier.fillMaxSize().background(PizzaYellow),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(48.dp))

        Text(
                "PIZZERIA",
                color = PizzaRed,
                fontSize = 42.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 8.sp
        )

        Spacer(Modifier.height(12.dp))

        // Cho thay app da biet dang nhap bang tai khoan nao
        Text(firebaseAuth.currentUser?.email.orEmpty(), fontSize = 14.sp, color = PizzaGrey)

        Spacer(Modifier.height(28.dp))

        Text(
                "Delivering\nDeliciousness right\nto your door!",
                color = PizzaDark,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 38.sp,
                textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(28.dp))

        PagerDots()

        Spacer(Modifier.height(24.dp))

        Box(Modifier.padding(horizontal = 80.dp)) {
            AuthButton("START ORDER") {
                Toast.makeText(context, "Chức năng đặt hàng chưa nằm trong Lab 8", Toast.LENGTH_SHORT)
                        .show()
            }
        }

        Spacer(Modifier.height(12.dp))

        Box(Modifier.padding(horizontal = 80.dp)) {
            AuthButton("SignOut") {
                firebaseAuth.signOut()
                navController.navigate(Screen.SignIn.route) {
                    // Xoa het ngan xep de bam Back khong quay lai duoc Home
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            }
        }

        Spacer(Modifier.weight(1f))

        // Anh nguoi giao hang o duoi cung.
        // Muon thay anh that: chep file vao res/drawable roi sua R.drawable.delivery_illustration.
        Image(
                painter = painterResource(R.drawable.delivery_illustration),
                contentDescription = "Nguoi giao pizza",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
        )
    }
}

/** Ba cham chi trang thai trang, cham giua dang duoc chon. */
@Composable
private fun PagerDots() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        repeat(3) { index ->
            Box(
                    Modifier.size(if (index == 1) 12.dp else 10.dp)
                            .clip(CircleShape)
                            .background(if (index == 1) PizzaDark else Color.White.copy(alpha = 0.55f))
            )
        }
    }
}
