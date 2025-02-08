package com.jp.chatapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.jp.chatapp.presentation.navigation.ChatRoute
import com.jp.chatapp.presentation.screens.utils.ReceivedChat
import com.jp.chatapp.presentation.screens.utils.SendChat
import com.jp.chatapp.presentation.utils.dateFormatter
import com.jp.chatapp.presentation.utils.timeFormatter
import com.jp.chatapp.presentation.viewmodel.ChatViewModel
import com.jp.chatapp.presentation.viewmodel.MainViewmodel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    receiver: ChatRoute,
    viewModel: ChatViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val state = rememberLazyListState()
    var msg by remember {
        mutableStateOf("")
    }

    LaunchedEffect(true) {
        viewModel.receiveChats()
    }
    LaunchedEffect(Unit) {
        viewModel.getChats(receiver.receiver)
    }

    val chats = viewModel.chats.collectAsStateWithLifecycle()
    val user by viewModel.userInfo.collectAsStateWithLifecycle()
    LaunchedEffect(chats.value) {
        state.animateScrollToItem(if (chats.value.isNotEmpty()) chats.value.size - 1 else 0)
    }

    LaunchedEffect(Unit) {

        viewModel.getUserInfo( receiver.receiver)
        viewModel.clearList()

    }




    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                if (user != null) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        AsyncImage(
                            user!!.profileImg,
                            contentDescription = "user profile ${user!!.name}",
                            modifier = Modifier
//                                .padding(5.dp)
                                .size(50.dp)
                                .clip(
                                    CircleShape
                                ), contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth(.8f),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(user!!.name, style = MaterialTheme.typography.titleLarge)
                            Text(
                                if (user!!.isOnline) "Online" else "Last seen : ${timeFormatter(user!!.lastSeen)} ${dateFormatter(user!!.lastSeen)}",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }


                } else {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        AsyncImage(
                            receiver.profileImg,
                            contentDescription = "user profile ${receiver.name}",
                            modifier = Modifier
//                                .padding(5.dp)
                                .size(50.dp)
                                .clip(
                                    CircleShape
                                ), contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth(.8f),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(receiver.name, style = MaterialTheme.typography.titleLarge)
                            Text(
                                if (receiver.isOnline) "Online" else dateFormatter(receiver.lastSeen),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }

            }, navigationIcon = {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back",
                        modifier = Modifier.size(30.dp)
                    )
                }
            })
        }


    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(it),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyColumn(
                state = state,
                modifier = Modifier
                    .fillMaxHeight(.9f)
                    .fillMaxWidth()
            ) {
                items(chats.value) { it ->
                    val chat = it.collectAsStateWithLifecycle().value
                    if (chat.chat.receiver != receiver.receiver) {
//                        ReceivedChat(
//                            time = chat.chat.createdAt,
//                            content = chat.chat.content,
//                            bio = chat.senderBio
//                        )
                        SendChat(
                            time = chat.chat.createdAt,
                            content = chat.chat.content,
                            bio = chat.senderBio,
                            isSent =  false
                        )
                    } else {
                        SendChat(
                            time = chat.chat.createdAt,
                            content = chat.chat.content,
                            bio = "You",
                           isSent =  true
                        )

                    }
                    Spacer(
                        modifier = Modifier
                            .height(2.dp)
                            .background(Color.Gray)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = msg,
                    onValueChange = { msg = it },
                    modifier = Modifier.fillMaxWidth(.85f),
                    shape = RoundedCornerShape(30.dp),
                    maxLines = 3,
                    placeholder = {
                        Text(
                            "Message",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                )
                IconButton(
                    onClick = {
                        if(msg.isNotBlank()){
                            viewModel.sendMessage(
                                receiver.phone,
                                content = msg.trim()
                            )
                        }
                        msg = ""
                        Toast.makeText(context, receiver.phone, Toast.LENGTH_SHORT).show()
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                      disabledContainerColor =   MaterialTheme.colorScheme.secondaryContainer,
                       containerColor =  MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.size(50.dp), enabled = msg.isNotBlank()

                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp), tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }


        }
    }
}