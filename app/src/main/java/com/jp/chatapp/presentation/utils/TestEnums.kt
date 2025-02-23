package com.jp.chatapp.presentation.utils

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.jp.chatapp.data.utils.MessageStatus
import com.jp.chatapp.presentation.viewmodel.MainViewmodel
import kotlin.reflect.typeOf

@Composable
fun TestEnum(mainViewmodel: MainViewmodel) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = {
            Toast.makeText(context, MessageStatus.READ.toString(), Toast.LENGTH_SHORT).show()

        }) {
            Text("Test")
        }
    }
}