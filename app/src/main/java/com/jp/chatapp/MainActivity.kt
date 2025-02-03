package com.jp.chatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import com.example.compose.AppTheme
import com.jp.chatapp.domain.state.ResultState
import com.jp.chatapp.presentation.navigation.App
import com.jp.chatapp.presentation.screens.SplashScreen
import com.jp.chatapp.presentation.viewmodel.ChatViewModel
import com.jp.chatapp.presentation.viewmodel.HomeViewModel
import com.jp.chatapp.presentation.viewmodel.MainViewmodel
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val mainViewmodel: MainViewmodel by inject<MainViewmodel>()
    private val chatViewModel: ChatViewModel by inject<ChatViewModel>()
    private val homeViewModel: HomeViewModel by inject<HomeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {

            val accessToken = mainViewmodel.accessToken.collectAsState().value
            AppTheme(dynamicColor = true) {

                when (accessToken) {
                    is ResultState.Error -> {

                    }

                    ResultState.Loading -> {
                        SplashScreen()

                    }

                    is ResultState.Success -> {
                        App(
                            mainViewmodel = mainViewmodel,
                            accessToken = accessToken.data,
                            chatViewModel = chatViewModel,
                            homeViewModel = homeViewModel
                        )

                    }
                }
            }
        }


    }

    override fun onResume() {
        super.onResume()
        mainViewmodel.connect()
        homeViewModel.receiveChatList()
        chatViewModel.receiveChats()
        chatViewModel.receiverUserInfo()

    }

    override fun onPause() {
        mainViewmodel.removeSocket()
        super.onPause()

    }
}
