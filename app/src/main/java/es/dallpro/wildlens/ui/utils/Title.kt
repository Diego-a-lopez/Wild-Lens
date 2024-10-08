package es.dallpro.wildlens.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import es.dallpro.wildlens.R


@Composable
fun Title(modifier: Modifier = Modifier) {
    val darkTheme: Boolean = isSystemInDarkTheme()
    if (darkTheme) {
        Image(
            painter = painterResource(id = R.drawable.dark_title),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = modifier.graphicsLayer(
                scaleX = 2f,
                scaleY = 2f
            )
        )
    }
    else {
        Image(
            painter = painterResource(id = R.drawable.title),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = modifier.graphicsLayer(
                scaleX = 2f,
                scaleY = 2f
            )
        )
    }
}