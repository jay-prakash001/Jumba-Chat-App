package com.jp.chatapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.jp.chatapp.R
import com.jp.chatapp.presentation.navigation.HomeRoute
import com.jp.chatapp.presentation.navigation.Login
import com.jp.chatapp.presentation.viewmodel.MainViewmodel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
//    modifier: Modifier = Modifier,
//    navController: NavController,
//    mainViewmodel: MainViewmodel
) {
//    val token = mainViewmodel.accessToken.collectAsStateWithLifecycle()
//    LaunchedEffect(token.value) {
//        delay(1500)
//        println(token.value)
//        println("===++------------------")
//        if (token.value == null) {
//            navController.navigate(Login)
//        } else {
//            navController.navigate(HomeRoute())
//        }
//    }
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.app_icon),
            contentDescription = "app icon", modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(50.dp))
        LinearProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.width(200.dp),
            trackColor = MaterialTheme.colorScheme.onPrimary
        )
    }
}