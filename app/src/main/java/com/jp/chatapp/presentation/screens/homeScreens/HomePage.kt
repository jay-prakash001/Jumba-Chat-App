package com.jp.chatapp.old.presentation.screens.homeScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.jp.chatapp.presentation.navigation.ChatRoute
import com.jp.chatapp.presentation.screens.utils.ChatListItem
import com.jp.chatapp.presentation.viewmodel.HomeViewModel
import com.jp.chatapp.presentation.viewmodel.MainViewmodel

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    mainViewmodel: MainViewmodel,
    navController: NavHostController,
    token: String?
) {

    LaunchedEffect(Unit) {
        println(token + " 8888888888888888888")
    if(!token.isNullOrBlank()){

        viewModel.getChatList(token)
    }
    }

    val chatList = viewModel.chatList.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        println("chatlist $chatList")

//        item{
//            Button(onClick = {
//                viewModel.sendMessage(mainViewmodel.accessToken.value!!, receiver = "0", UUID.randomUUID().toString())
//            }) { }
//        }
        items(chatList.value) { user->

            val it = user.collectAsStateWithLifecycle().value
            ChatListItem(chatUserInfo = it) {
                navController.navigate(
                    ChatRoute(
                        name = it.name,
                        lastSeen = it.lastSeen,
                        receiver = it.user_id,
                        bio = it.bio,
                        phone = it.phone,
                        profileImg = it.profileImg
                    )
                )
            }
//
//            ChatList(chatUserInfo = it) {
//                navController.navigate(
//                    ChatRoute(
//                        name = it.name,
//                        lastSeen = it.lastSeen,
//                        receiver = it.user_id,
//                        bio = it.bio,
//                        phone = it.phone,
//                        profileImg = it.profileImg
//                    )
//                )
//            }
        }
    }
}

