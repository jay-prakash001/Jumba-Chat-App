package com.jp.chatapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jp.chatapp.presentation.screens.AddContactScreen
import com.jp.chatapp.presentation.screens.ChatScreen
import com.jp.chatapp.presentation.screens.HomeScreen
import com.jp.chatapp.presentation.screens.LoginScreen
import com.jp.chatapp.presentation.viewmodel.ChatViewModel
import com.jp.chatapp.presentation.viewmodel.HomeViewModel
import com.jp.chatapp.presentation.viewmodel.MainViewmodel
import org.koin.androidx.compose.getViewModel

@Composable
fun App(
    modifier: Modifier = Modifier,
    mainViewmodel: MainViewmodel,
    accessToken: String?,
    chatViewModel: ChatViewModel,
    homeViewModel: HomeViewModel
) {
//    KoinContext {
    val navController = rememberNavController()
//        val mainViewmodel = getViewModel<MainViewmodel>()
//        val homeViewModel = getViewModel<HomeViewModel>()
        val accessToken0 = mainViewmodel.accessToken.collectAsState().value


    val startDestination = if (accessToken != null) HomeRoute(accessToken) else Login
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
//            composable<Splash> {
////                SplashScreen(navController = navController, mainViewmodel = mainViewmodel)
//            }
        composable<Login> {
            LoginScreen(navController = navController, mainViewmodel = mainViewmodel)
        }

        composable<HomeRoute> {
//                val token = if (it.toRoute<HomeRoute>().token.isNotBlank()) {
//                    it.toRoute<HomeRoute>().token
//                } else {
//                    accessToken
//                }

            println("5555555555 $accessToken")

            HomeScreen(
                navController = navController,
                token = accessToken!!,
                mainViewmodel = mainViewmodel,
                viewmodel = homeViewModel
            )
        }

        composable<ChatRoute> {
            val receiver = it.toRoute<ChatRoute>()
            ChatScreen(
                navController = navController,
                receiver = receiver, token = accessToken,
                mainViewModel = mainViewmodel, viewModel = chatViewModel
            )
        }

        composable<AddContactRoute> {
            AddContactScreen(
                homeViewModel = homeViewModel,
                navController = navController,
                token = accessToken!!
            )

        }
    }

//    }
}