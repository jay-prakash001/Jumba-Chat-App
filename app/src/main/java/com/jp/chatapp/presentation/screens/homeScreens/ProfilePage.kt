package com.jp.chatapp.presentation.screens.homeScreens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.jp.chatapp.presentation.navigation.Splash
import com.jp.chatapp.presentation.utils.fileType
import com.jp.chatapp.presentation.utils.getFileBytesFromUri
import com.jp.chatapp.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(homeViewModel: HomeViewModel, navController: NavController) {
    val context = LocalContext.current
    val newImg = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        newImg.value = it

    }
    LaunchedEffect(Unit) {
        homeViewModel.getProfile()

    }
    val profile = homeViewModel.profile.collectAsStateWithLifecycle().value ?: return
    val isLoading = remember { mutableStateOf(false) }
    LaunchedEffect(profile.profileImg) {
        newImg.value = null
        isLoading.value = false
    }


    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier.size(200.dp), contentAlignment = Alignment.BottomEnd) {
            AsyncImage(
                if (newImg.value != null) newImg.value else profile.profileImg,
                contentDescription = "profile image",
                modifier = Modifier
                    .size(200.dp)
                    .clip(
                        CircleShape
                    ),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = {
                    launcher.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primaryContainer),
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "edit profile Image",
                    modifier = Modifier.size(30.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }


        }
        Spacer(Modifier.height(10.dp))
        Text(
            if (profile.isOnline) "Online" else "Offline",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(40.dp))

        Text(profile.bio, style = MaterialTheme.typography.displayLarge)
        Spacer(Modifier.height(10.dp))
        Text(profile.phone, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(40.dp))
        ElevatedButton(
            onClick = {
                if (newImg.value != null) {
                    homeViewModel.updateProfile(
                        getFileBytesFromUri(context, newImg.value!!),
                        fileType(context, newImg.value!!)
                    )
                    isLoading.value = true

                }
            },
            colors = ButtonDefaults.buttonColors(
                MaterialTheme.colorScheme.surfaceTint
            ),
            modifier = Modifier.width(200.dp), enabled = newImg.value != null
        ) {
            if (isLoading.value) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onSurface,
                    trackColor = MaterialTheme.colorScheme.background,
                    modifier = Modifier.size(20.dp), strokeWidth = 2.dp
                )
            } else {

                Text("Update")
            }
        }

        Spacer(Modifier.height(10.dp))
        Spacer(Modifier.height(10.dp))
        ElevatedButton(
            onClick = {
                homeViewModel.logout()
                navController.navigate(Splash)

            },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
            modifier = Modifier.width(200.dp)
        ) {
            Text("Log Out")
        }


    }

}
