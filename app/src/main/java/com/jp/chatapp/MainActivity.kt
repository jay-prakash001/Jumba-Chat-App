package com.jp.chatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.res.painterResource
import com.example.compose.AppTheme
import com.jp.chatapp.presentation.navigation.App
import com.jp.chatapp.presentation.utils.TestDataStore
import com.jp.chatapp.presentation.utils.TestEnum
import com.jp.chatapp.presentation.utils.TexturedBackGround
import com.jp.chatapp.presentation.utils.requestPermission
import com.jp.chatapp.presentation.viewmodel.ChatViewModel
import com.jp.chatapp.presentation.viewmodel.HomeViewModel
import com.jp.chatapp.presentation.viewmodel.LoginViewmodel
import com.jp.chatapp.presentation.viewmodel.MainViewmodel
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val mainViewmodel: MainViewmodel by inject<MainViewmodel>()
    private val chatViewModel: ChatViewModel by inject<ChatViewModel>()
    private val homeViewModel: HomeViewModel by inject<HomeViewModel>()
    private val loginViewmodel: LoginViewmodel by inject<LoginViewmodel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission(this, this)
        enableEdgeToEdge()
        setContent {
            AppTheme(dynamicColor = false) {
//
//                App(
//                    mainViewmodel = mainViewmodel,
//                    chatViewModel = chatViewModel,
//                    homeViewModel = homeViewModel,
//                    loginViewmodel = loginViewmodel
//                )
//
                TestEnum(mainViewmodel)
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
