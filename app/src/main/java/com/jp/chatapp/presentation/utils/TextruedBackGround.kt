package com.jp.chatapp.presentation.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jp.chatapp.R
import kotlin.random.Random

@Composable
fun TexturedBackGround(modifier: Modifier = Modifier) {
    val images = listOf(R.drawable.chat_bg, R.drawable.chat_bgr, R.drawable.chat_bgt)
    LazyVerticalGrid(columns = GridCells.Adaptive(40.dp), modifier = modifier.fillMaxSize().padding(10.dp)) {
        items(1500){

            Icon(painterResource(images[Random.nextInt(0,images.size)]), contentDescription = "bg", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.rotate(Random.nextFloat() * Random.nextInt(45,800)).alpha(.4f))
        }
    }
}