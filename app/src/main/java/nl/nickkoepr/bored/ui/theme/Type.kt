package nl.nickkoepr.bored.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import nl.nickkoepr.bored.R

private val robotoFontFamily = FontFamily(
    Font(
        R.font.roboto_black,
        weight = FontWeight.ExtraBold,
        style = FontStyle.Normal
    ),
    Font(
        R.font.roboto_medium,
        weight = FontWeight.Medium,
        style = FontStyle.Normal
    ),
    Font(
        R.font.roboto_bold,
        weight = FontWeight.Bold,
        style = FontStyle.Normal
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Medium,
        fontStyle = FontStyle.Normal,
        fontSize = 25.sp
    ), displayLarge = TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontStyle = FontStyle.Normal,
        fontSize = 40.sp,
        lineHeight = 50.sp
    ),
    labelMedium = TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal
    )
)