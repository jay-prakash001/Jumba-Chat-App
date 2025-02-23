package com.jp.chatapp.presentation.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jp.chatapp.presentation.viewmodel.HomeViewModel
import com.jp.chatapp.presentation.viewmodel.LoginViewmodel
import com.jp.chatapp.presentation.viewmodel.MainViewmodel
import com.jp.chatapp.utils.ACCESS_TOKEN

@Composable
fun TestDataStore(
    modifier: Modifier = Modifier,
    mainViewmodel: MainViewmodel,
    loginViewmodel: LoginViewmodel, homeViewModel: HomeViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val list = remember { mutableStateOf(listOf("")) }

        Button(onClick = {
            mainViewmodel.connect()
//            mainViewmodel.receiveChatList {
//                list.value = list.value + it
//
//            }
        }) {

            Text("Connect")
        }


        Text(list.value.toString())
//        val key by remember { mutableStateOf(ACCESS_TOKEN) }
//        var value by remember { mutableStateOf("") }
//
//        val mToken = mainViewmodel.accessToken.collectAsStateWithLifecycle()
//        val lToken = loginViewmodel.accessToken.collectAsStateWithLifecycle()
//
//        val phone = loginViewmodel.phone.collectAsStateWithLifecycle()
//        val chatList = homeViewModel.chatList.collectAsStateWithLifecycle()
//        OutlinedTextField(
//            value = value,
//            onValueChange = { value = it },
//            modifier = Modifier.fillMaxWidth(.8f),
//            maxLines = 1
//        )
//        Spacer(Modifier.height(20.dp))
//        Button(onClick = { loginViewmodel.saveToken(key, value) }) {
//
//
//            Text("save")
//        }
//
//        OutlinedTextField(
//            value = phone.value,
//            onValueChange = { loginViewmodel.updatePhone(it) },
//            modifier = Modifier.fillMaxWidth(.8f),
//            maxLines = 1
//        )
//        Button(onClick = {
//            loginViewmodel.login()
//        }) {
//
//            Text("Login")
//        }
//        Spacer(Modifier.height(20.dp))
//        Text("ChatList : ${chatList}")
//
//        Spacer(Modifier.height(20.dp))
//        Text("MainToken : ${mToken.value}")
//

    }
}