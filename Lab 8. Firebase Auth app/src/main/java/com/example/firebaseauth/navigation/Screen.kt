package com.example.firebaseauth.navigation

/**
 * Khai bao 3 man hinh cua app kem duong dan (route) dung cho NavHost.
 *
 * De bai viet la `rout`, o day sua lai thanh `route` cho dung chinh ta.
 */
sealed class Screen(val route: String) {
    object SignIn : Screen("signin")

    object SignUp : Screen("signup")

    object Home : Screen("home")
}
