package com.jp.chatapp.presentation.screens.homeScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.jp.chatapp.presentation.navigation.AddContactRoute
import com.jp.chatapp.presentation.navigation.ChatRoute
import com.jp.chatapp.presentation.screens.utils.ContactListItem
import com.jp.chatapp.presentation.viewmodel.HomeViewModel

@Composable
fun ContactsPage(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    navController: NavHostController
) {

    LaunchedEffect(Unit) {
        viewModel.getContacts()
    }
    val contacts = viewModel.contactList.collectAsStateWithLifecycle().value
    LaunchedEffect(contacts) {
        viewModel.updateContacts()
    }

    LazyColumn(
        modifier = Modifier.padding(2.dp).fillMaxSize(),
//        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .clickable {
                        navController.navigate(AddContactRoute)
                    }
                    .border(
                        1.dp, MaterialTheme.colorScheme.outlineVariant,
                        RoundedCornerShape(10.dp)
                    ),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    Icons.Filled.AddCircle,
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = "add new contact",
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onSecondary)
                )
                Text("New contact", style = MaterialTheme.typography.labelLarge)
            }

        }


        items(contacts) {

            ContactListItem(navController,contact = it) {
                navController.navigate(
                    ChatRoute(
                        bio = it.bio,
                        phone = it.phone,
                        profileImg = it.profileImg,
                        name = it.name
                    )
                )
            }
        }


    }
}