package com.example.firebaseauth.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebaseauth.screens.HomeScreen
import com.example.firebaseauth.screens.SignIn
import com.example.firebaseauth.screens.SignUp

/**
 * Ham dieu huong dung chung, duoc goi trong MainActivity.
 *
 * Man hinh dau tien la SignIn; dang nhap / dang ky thanh cong thi chuyen sang Home.
 */
@Composable
fun Mynavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.SignIn.route) {
        composable(Screen.SignIn.route) { SignIn(navController = navController) }
        composable(Screen.SignUp.route) { SignUp(navController = navController) }
        composable(Screen.Home.route) { HomeScreen(navController = navController) }
    }
}
