package com.jp.chatapp.presentation.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jp.chatapp.presentation.viewmodel.MainViewmodel

@Composable
fun TestEnum(mainViewmodel: MainViewmodel) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = { mainViewmodel.testEnum() }) {
            Text("Test")
        }
    }
}