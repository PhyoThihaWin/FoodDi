package com.pthw.food.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.pthw.food.theme.Dimens

/**
 * Created by P.T.H.W on 29/03/2024.
 */
@Composable
fun TitleTextView(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Center,
    fontSize: TextUnit = Dimens.TEXT_XLARGE
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        textAlign = textAlign,
        fontSize = fontSize,
        fontWeight = FontWeight.SemiBold,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}