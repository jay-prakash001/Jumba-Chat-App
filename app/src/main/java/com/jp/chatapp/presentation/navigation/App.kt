package com.jp.chatapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jp.chatapp.presentation.screens.AddContactScreen
import com.jp.chatapp.presentation.screens.ChatScreen
import com.jp.chatapp.presentation.screens.HomeScreen
import com.jp.chatapp.presentation.screens.LoginScreen
import com.jp.chatapp.presentation.screens.ShowImageScreen
import com.jp.chatapp.presentation.screens.SplashScreen
import com.jp.chatapp.presentation.viewmodel.ChatViewModel
import com.jp.chatapp.presentation.viewmodel.HomeViewModel
import com.jp.chatapp.presentation.viewmodel.LoginViewmodel
import com.jp.chatapp.presentation.viewmodel.MainViewmodel
import org.koin.androidx.compose.koinViewModel

@Composable
fun App(
    modifier: Modifier = Modifier,
    mainViewmodel: MainViewmodel,
    chatViewModel: ChatViewModel,
    homeViewModel: HomeViewModel,
    loginViewmodel: LoginViewmodel = koinViewModel()
) {

    val startDestination = Splash
    val navController = rememberNavController()



    NavHost(navController = navController, startDestination = startDestination) {
        composable<Splash> {
            SplashScreen(navController = navController, mainViewmodel = mainViewmodel)
        }


        composable<Login> {

            LoginScreen(
                navController = navController,
                viewModel = loginViewmodel,
                mainViewmodel = mainViewmodel,
                homeViewModel = homeViewModel
            )
        }
        composable<Home> {

            HomeScreen(
                navController = navController,
                homeViewModel = homeViewModel,
                mainViewmodel = mainViewmodel
            )
        }

        composable<ChatRoute> {
            val receiver = it.toRoute<ChatRoute>()
            ChatScreen(
                navController = navController,
                receiver = receiver,
                viewModel = chatViewModel
            )
        }

        composable<AddContactRoute> {
            AddContactScreen(
                homeViewModel = homeViewModel,
                navController = navController,
            )

        }
        composable<ShowImage> {
            val image = it.toRoute<ShowImage>().img
            ShowImageScreen(img = image, navController = navController)
        }

    }

}