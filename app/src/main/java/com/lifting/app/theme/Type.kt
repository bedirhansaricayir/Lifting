package com.lifting.app.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.lifting.app.R

val macPawFixel = FontFamily(
    Font(R.font.fixeltext_regular),
    Font(R.font.fixeltext_bold, FontWeight.Bold),
    Font(R.font.fixeltext_medium, FontWeight.Medium)
)
// Set of Material typography styles to start with
val Typography = Typography(
    headlineMedium = TextStyle(
        fontFamily = macPawFixel,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = macPawFixel,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = macPawFixel,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = macPawFixel,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = macPawFixel,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp
    ),
    titleSmall = TextStyle(
        fontFamily = macPawFixel,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    labelMedium = TextStyle(
        fontFamily = macPawFixel,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = macPawFixel,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )

)