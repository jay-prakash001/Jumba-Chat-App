package com.jp.chatapp.presentation.screens.utils

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.jp.chatapp.R
import com.jp.chatapp.data.room.entities.Contact
import com.jp.chatapp.domain.models.chatList.ChatUserInfo
import com.jp.chatapp.domain.models.contactList.ContactUserInfo
import com.jp.chatapp.domain.models.contactList.SingleContact
import com.jp.chatapp.presentation.navigation.ShowImage
import com.jp.chatapp.presentation.utils.timeFormatter

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ChatListItem(
    modifier: Modifier = Modifier,
    chatUserInfo: ChatUserInfo,
    navController: NavController,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    Card(
        onClick = onClick,
        modifier = modifier.padding(2.dp),
        shape = RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            SubcomposeAsyncImage(
                chatUserInfo.profileImg,
                contentDescription = chatUserInfo.name,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .clickable {
                        Toast
                            .makeText(context, "Loading Profile Image", Toast.LENGTH_SHORT)
                            .show()
                        if (chatUserInfo.profileImg.isNullOrBlank()) {
                            Toast
                                .makeText(
                                    context,
                                    "Error Loading Profile Image",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        } else {
                            navController.navigate(ShowImage(chatUserInfo.profileImg))
                        }

                    }, contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    chatUserInfo.name.capitalize(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.wrapContentWidth()

                )
                Row {

                    Text(
                        if (chatUserInfo.lastChat?.isSended!!) {
                            "You :"
                        } else "",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold

                    )

                    Text(
                        timeFormatter(chatUserInfo.lastChat.time) + " : " + if (chatUserInfo.lastChat.content.isNotBlank() &&
                            chatUserInfo.lastChat.content.length > 40
                        ) chatUserInfo.lastChat.content.trim()
                            .substring(0, 30) else chatUserInfo.lastChat.content.trim(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Thin

                    )
                }

            }
        }
    }
}


@Composable
fun ContactListItem(navController: NavController, contact: Contact, onClick: () -> Unit) {

    Card(
        onClick = onClick,
        modifier = Modifier.padding(2.dp),
        shape = RoundedCornerShape(5.dp),

        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            val img = if (contact.profileImg.isNullOrBlank()) {
                R.drawable.profile
            } else {

                contact.profileImg
            }
            SubcomposeAsyncImage(
               img,
                contentDescription = contact.name,
                modifier = Modifier
                    .size(60.dp)
                    .padding(2.dp)
                    .clip(CircleShape)
                    .clickable {
                        navController.navigate(ShowImage(img.toString()))
                    }, contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    contact.name.capitalize(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth()

                )
                Text(
                    contact.bio.capitalize(),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Light, modifier = Modifier.fillMaxWidth()

                )

            }
        }
    }
}


@Composable
fun SendChat(
    modifier: Modifier = Modifier,
    time: String,
    bio: String,
    content: String,
    isSent: Boolean
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = if (isSent) Arrangement.End else Arrangement.Start
    ) {

        val bg =
            if (isSent) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondaryContainer

        val contentColor =
            if (isSent) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSecondaryContainer
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .widthIn(70.dp, 350.dp)
                .padding(horizontal = 10.dp, vertical = 2.dp)

                .clip(
                    RoundedCornerShape(
                        bottomStart = 15.dp,
                        bottomEnd = if (isSent) 0.dp else 15.dp,
                        topStart = if (isSent) 15.dp else 0.dp,
                        topEnd = 15.dp
                    )
                )

                .background(
                    bg
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 10.dp)
                    .wrapContentWidth()
                    .widthIn(50.dp, 350.dp)
            ) {
                Text(
                    bio,
                    style = MaterialTheme.typography.bodySmall,
                    color = contentColor
                )
                Text(
                    content,
                    style = MaterialTheme.typography.bodyLarge,
                    color = contentColor
                )
                Row(
                    modifier = Modifier.wrapContentWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        timeFormatter(time),
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 8.sp,
                        color = contentColor
                    )
                }

            }
        }
    }

}

@Composable
fun ReceivedChat(modifier: Modifier = Modifier, time: String, bio: String, content: String) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {


        Row(
            modifier = Modifier
                .wrapContentWidth()
                .widthIn(80.dp)
                .padding(horizontal = 10.dp, vertical = 2.dp)

                .clip(
                    RoundedCornerShape(
                        bottomStart = 0.dp,
                        bottomEnd = 15.dp,
                        topStart = 15.dp,
                        topEnd = 15.dp
                    )
                )

                .background(MaterialTheme.colorScheme.primaryContainer),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 10.dp)
                    .wrapContentWidth()
            ) {
                Text(
                    bio,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    content,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    timeFormatter(time),
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 8.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

            }
        }
    }

}