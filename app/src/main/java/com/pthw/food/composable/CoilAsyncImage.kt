package com.pthw.food.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

/**
 * Created by P.T.H.W on 04/04/2024.
 */

@Composable
fun CoilAsyncImage(
    imageUrl: Any,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    shape: Shape = RoundedCornerShape(0)
) {
    AsyncImage(
        modifier = modifier
            .fillMaxSize()
            .clip(shape),
        model = imageUrl,
        placeholder = ColorPainter(Color.DarkGray),
        error = ColorPainter(Color.DarkGray),
        contentScale = contentScale,
        contentDescription = null,
    )
}