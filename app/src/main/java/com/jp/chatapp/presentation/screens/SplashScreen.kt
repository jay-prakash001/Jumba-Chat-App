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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.jp.chatapp.R
import com.jp.chatapp.domain.state.ResultState
import com.jp.chatapp.presentation.navigation.Home
import com.jp.chatapp.presentation.navigation.HomeRoute
import com.jp.chatapp.presentation.navigation.Login
import com.jp.chatapp.presentation.navigation.Splash
import com.jp.chatapp.presentation.viewmodel.MainViewmodel

@Composable
fun SplashScreen(navController: NavHostController, mainViewmodel: MainViewmodel) {

    val accessToken by mainViewmodel.accessToken.collectAsState()
    LaunchedEffect(accessToken) {

        when (accessToken) {
            is ResultState.Error -> {
                navController.navigate(Login) {
                    popUpTo(0)
                }
            }

            is ResultState.Success -> {

                navController.navigate(Home) {
                    popUpTo(0)
                }
            }

            else -> {

            }
        }

    }

    if (accessToken is ResultState.Loading) {
        Splash()
    }
}

@Composable
fun Splash(modifier: Modifier = Modifier) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.app_icon),
            contentDescription = "app icon", modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(50.dp))
        LinearProgressIndicator(
            modifier = Modifier.width(200.dp),
        )
    }
}