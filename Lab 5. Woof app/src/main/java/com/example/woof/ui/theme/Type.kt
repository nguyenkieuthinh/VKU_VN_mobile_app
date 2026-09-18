package com.example.woof.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.woof.R

// Font đậm (dùng cho tiêu đề)
val LoveDays = FontFamily(Font(R.font.love_days))
val Abril = FontFamily(Font(R.font.abril_fatface_regular))

// Font nhỏ (dùng cho nội dung, label)
val ArianaVioleta = FontFamily(Font(R.font.ariana_violeta))

val Typography =
        Typography(
                displayLarge =
                        TextStyle(
                                fontFamily = Abril,
                                fontWeight = FontWeight.Normal,
                                fontSize = 36.sp
                        ),
                titleLarge =
                        TextStyle(
                                fontFamily = LoveDays,
                                fontWeight = FontWeight.Normal,
                                fontSize = 22.sp
                        ),
                bodyLarge =
                        TextStyle(
                                fontFamily = ArianaVioleta,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp
                        ),
                labelSmall =
                        TextStyle(
                                fontFamily = ArianaVioleta,
                                fontWeight = FontWeight.Normal,
                                fontSize = 11.sp
                        )
        )
