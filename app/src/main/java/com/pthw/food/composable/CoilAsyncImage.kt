package com.pthw.food.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.imageLoader
import coil.util.DebugLogger
import com.pthw.food.R

/**
 * Created by P.T.H.W on 04/04/2024.
 */

@Composable
fun CoilAsyncImage(
    imageUrl: Any,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val imageLoader = LocalContext.current.imageLoader.newBuilder()
        .logger(DebugLogger())
        .build()

    AsyncImage(
        modifier = modifier,
        model = imageUrl,
        placeholder = painterResource(id = R.drawable.logoblack),
        error = painterResource(id = R.drawable.logoblack),
        contentScale = contentScale,
        contentDescription = null,
        imageLoader = imageLoader
    )
}