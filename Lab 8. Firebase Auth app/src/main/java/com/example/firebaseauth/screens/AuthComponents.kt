package com.example.firebaseauth.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebaseauth.R
import com.example.firebaseauth.ui.theme.FacebookBlue
import com.example.firebaseauth.ui.theme.GoogleBlue
import com.example.firebaseauth.ui.theme.PizzaRed

/**
 * Anh dau trang (chiec pizza) kem duong luon song o day.
 *
 * Muon thay bang anh that: chep file vao res/drawable (vi du pizza.jpg)
 * roi sua R.drawable.pizza_header ben duoi thanh R.drawable.pizza.
 */
@Composable
fun PizzaHeader(waveColor: Color = Color.White, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxWidth().height(270.dp)) {
        Image(
                painter = painterResource(R.drawable.pizza_header),
                contentDescription = "Anh pizza",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
        )

        // Duong cong: ve hinh mau trang de che phan duoi cua tam anh
        Canvas(Modifier.fillMaxWidth().height(80.dp).align(Alignment.BottomCenter)) {
            val w = size.width
            val h = size.height
            val wave =
                    Path().apply {
                        moveTo(0f, h)
                        lineTo(0f, h * 0.55f)
                        cubicTo(w * 0.30f, -h * 0.15f, w * 0.70f, h * 1.40f, w, h * 0.45f)
                        lineTo(w, h)
                        close()
                    }
            drawPath(wave, waveColor)
        }
    }
}

/** O nhap lieu dung chung cho ca hai man hinh dang nhap / dang ky. */
@Composable
fun AuthTextField(
        value: String,
        onValueChange: (String) -> Unit,
        label: String,
        modifier: Modifier = Modifier,
        keyboardType: KeyboardType = KeyboardType.Text,
        isPassword: Boolean = false
) {
    OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation =
                    if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon =
                    if (isPassword) {
                        { Icon(Icons.Filled.Lock, contentDescription = null, tint = PizzaRed) }
                    } else null,
            colors =
                    OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PizzaRed,
                            unfocusedBorderColor = PizzaRed,
                            focusedLabelColor = PizzaRed,
                            unfocusedLabelColor = PizzaRed,
                            cursorColor = PizzaRed
                    ),
            modifier = modifier.fillMaxWidth()
    )
}

/** Nut bam chinh (Sign In / Sign Up / Sign Out). */
@Composable
fun AuthButton(
        text: String,
        modifier: Modifier = Modifier,
        containerColor: Color = PizzaRed,
        onClick: () -> Unit
) {
    Button(
            onClick = onClick,
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = containerColor),
            modifier = modifier.fillMaxWidth().height(52.dp)
    ) {
        Text(text, fontSize = 17.sp, fontWeight = FontWeight.Bold)
    }
}

/**
 * Hai nut tron Google / Facebook cho giong anh thiet ke.
 *
 * Chung CHI LA GIAO DIEN, chua noi chuc nang: muon dang nhap that bang Google
 * phai bat provider Google tren Firebase Console va them SHA-1 cua may
 * (lay bang lenh ./gradlew signingReport).
 */
@Composable
fun SocialSignInRow(modifier: Modifier = Modifier) {
    Row(modifier, horizontalArrangement = Arrangement.spacedBy(24.dp)) {
        SocialCircle("G", GoogleBlue)
        SocialCircle("f", FacebookBlue)
    }
}

@Composable
private fun SocialCircle(letter: String, color: Color) {
    Box(
            Modifier.size(46.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(1.dp, Color(0xFFE0E0E0), CircleShape),
            contentAlignment = Alignment.Center
    ) {
        Text(letter, color = color, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}
