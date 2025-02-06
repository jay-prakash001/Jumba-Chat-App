package com.jp.chatapp.old.presentation.utils

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jp.chatapp.presentation.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

//    fun login(){
//        viewModelScope.launch {
//            webSocketManager.connect(URL)
//            webSocketManager.join(_phone.value)
//
////            ws.connect("http://10.0.2.2:9000")
////            ws.join(phone)
//        }
//    }



//
//@Composable
//fun TestKtor(modifier: Modifier = Modifier) {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//
//        val navController = rememberNavController()
//        LoginScreen(navController = navController,main)
//    }
//
//}

//
//@Composable
//fun Pref(modifier: Modifier = Modifier) {
//    val storePref : DataStorePref by inject()
//
//
//    val scope = rememberCoroutineScope()
//    var value by remember { mutableStateOf("") }
//    var text by remember { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        TextField(text, onValueChange = { text = it })
//        Button(onClick = {
//            scope.launch {
//
//                storePref.setToken("name", text)
//            }
//        }) {
//
//            Text("Submit")
//        }
//
//        Button(onClick = {
//            scope.launch {
//
//                storePref.getToken("name").collectLatest {
//
//                    value = it!!
//                }
//            }
//        }) {
//
//            Text("Get")
//        }
//        Text("Inputed Value ${value}")
//    }
//
//}
//



@Composable
private fun Test(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val contacts =
            listOf("9575833765", "6264974771", "8839934590", "6264596305")
        Text("Contacts", style = MaterialTheme.typography.headlineLarge)
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(contacts) { contact ->

                OutlinedCard(onClick = {
//                    navController.navigate((contact))
                }, modifier = Modifier.fillMaxWidth()) {

                    Text(contact, modifier = Modifier.padding(20.dp))

                }
            }
        }

//        val context = LocalContext.current
//
//        Button(onClick = {
//            Toast.makeText(context, "sent", Toast.LENGTH_SHORT).show()
//            ws.sendMessage("10","20", "hello")}) {
//            Text("Send")
//        }
    }
}


@Composable
fun HomeScreen0(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewmodel: HomeViewModel = koinViewModel()
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current

        Button(onClick = {
            Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show()
        }) {
            Text("Click")
        }
    }
}