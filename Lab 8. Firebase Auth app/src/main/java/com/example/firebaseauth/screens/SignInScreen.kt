package com.example.firebaseauth.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.firebaseauth.navigation.Screen
import com.example.firebaseauth.ui.theme.PizzaGrey
import com.example.firebaseauth.ui.theme.PizzaRed
import com.google.firebase.auth.FirebaseAuth

/**
 * Man hinh dang nhap.
 *
 * Luong: nhap email + password -> FirebaseAuth.signInWithEmailAndPassword()
 * -> thanh cong thi sang Home, that bai thi hien Toast loi.
 */
@Composable
fun SignIn(navController: NavController) {
    val context = LocalContext.current
    val firebaseAuth = remember { FirebaseAuth.getInstance() }

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    // Firebase tu luu phien dang nhap, nen mo lai app van biet ai dang dang nhap.
    // Neu da co nguoi dang nhap thi vao thang Home, khong bat dang nhap lai.
    LaunchedEffect(Unit) {
        if (firebaseAuth.currentUser != null) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.SignIn.route) { inclusive = true }
            }
        }
    }

    Column(
            modifier =
                    Modifier.fillMaxSize()
                            .background(Color.White)
                            .verticalScroll(rememberScrollState())
                            .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PizzaHeader()

        Spacer(Modifier.height(28.dp))

        Row {
            Text(
                    "Wellcome Back ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
            )
            Text("PIZZERIA!", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = PizzaRed)
        }

        Spacer(Modifier.height(28.dp))

        Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AuthTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    keyboardType = KeyboardType.Email
            )

            AuthTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    keyboardType = KeyboardType.Password,
                    isPassword = true
            )

            Spacer(Modifier.height(4.dp))

            AuthButton("Sign In") {
                signInWithEmail(firebaseAuth, email, password, context) {
                    navController.navigate(Screen.Home.route) {
                        // Dang nhap xong thi bo man hinh SignIn khoi ngan xep
                        popUpTo(Screen.SignIn.route) { inclusive = true }
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Is it first for you? ", fontSize = 14.sp, color = PizzaGrey)
            Text(
                    "Sign Up now!",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = PizzaRed,
                    modifier =
                            Modifier.clickable {
                                navController.navigate(Screen.SignUp.route)
                            }
            )
        }

        Spacer(Modifier.height(28.dp))

        Text("OR Sign In with", fontSize = 15.sp, color = PizzaGrey)

        Spacer(Modifier.height(16.dp))

        SocialSignInRow()

        Spacer(Modifier.height(32.dp))
    }
}

/**
 * Ham xu ly su kien bam nut Sign In, viet rieng cho de doc.
 *
 * @param onSuccess chay khi dang nhap thanh cong (thuong la chuyen man hinh).
 */
private fun signInWithEmail(
        firebaseAuth: FirebaseAuth,
        email: String,
        password: String,
        context: Context,
        onSuccess: () -> Unit
) {
    if (email.isNotEmpty() && password.isNotEmpty()) {
        firebaseAuth
                .signInWithEmailAndPassword(email.trim(), password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        Toast.makeText(context, task.exception.toString(), Toast.LENGTH_SHORT)
                                .show()
                    }
                }
    } else {
        Toast.makeText(context, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
    }
}
