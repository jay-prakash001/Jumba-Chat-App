package com.jp.chatapp.old.presentation.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.jp.chatapp.R
import com.jp.chatapp.presentation.navigation.HomeRoute
import com.jp.chatapp.presentation.utils.getFileBytesFromUri
import com.jp.chatapp.presentation.viewmodel.LoginViewmodel
import com.jp.chatapp.presentation.viewmodel.MainViewmodel
import com.jp.chatapp.utils.ACCESS_TOKEN
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: LoginViewmodel = koinViewModel(),
    mainViewmodel: MainViewmodel
) {
    val phone = viewModel.phone.collectAsStateWithLifecycle()
    val bio = viewModel.bio.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val result = rememberSaveable {
        mutableStateOf<Uri?>(null)
    }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        result.value = it
    }
    val scope = rememberCoroutineScope()
    val user = viewModel.user.collectAsStateWithLifecycle()

    LaunchedEffect(user.value) {
    println(user.value.toString() +  " USER ")
        if (user.value.data != null) {
            mainViewmodel.getToken(ACCESS_TOKEN,false)
            navController.navigate(HomeRoute(token = user.value.data!!.data.accessToken))
            navController.clearBackStack<HomeRoute>()

        }
    }



    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Text(
            stringResource(R.string.get_started),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 22.sp
        )
        Text(
            stringResource(R.string.login),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(10.dp))


        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.secondary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            AsyncImage(
                result.value,
                contentDescription = "selected profile Image",
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        launcher.launch(
                            PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentScale = ContentScale.Crop
            )

            IconButton(onClick = {
                launcher.launch(
                    PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }) {
                Icon(
                    if (result.value != null) Icons.Rounded.Edit else Icons.Default.Add,
                    contentDescription = "select profile image",
                    tint = if (result.value != null) MaterialTheme.colorScheme.onSecondary
                    else MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .size(50.dp)

                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = phone.value,
            onValueChange = { viewModel.updatePhone(it) },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth(.9f),
            singleLine = true,
            maxLines = 1,
            leadingIcon = {
                Icon(Icons.Default.Call, contentDescription = "call icon")
            },
            label = {
                Text(
                    stringResource(R.string.phone_lbl),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.labelSmall
                )
            },
            placeholder = {
                Text(
                    stringResource(R.string.phone_lbl),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelSmall
                )
            }

        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = bio.value,
            onValueChange = { viewModel.updateBio(it) },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth(.9f),
            singleLine = true,
            maxLines = 1,
            leadingIcon = {
                Icon(Icons.Default.Info, contentDescription = "info icon")
            },
            label = {
                Text(
                    stringResource(R.string.bio_lbl),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.labelSmall
                )
            },
            placeholder = {
                Text(
                    stringResource(R.string.bio_lbl),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelSmall
                )
            }

        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {

                viewModel.register(file = getFileBytesFromUri(context, result.value!!))


            },
            enabled = !user.value.isLoading,
            colors = ButtonDefaults.buttonColors(
                disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            modifier = Modifier
                .fillMaxWidth(.9f)
                .height(50.dp),
            shape = MaterialTheme.shapes.medium,

            ) {
            if (user.value.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    trackColor = MaterialTheme.colorScheme.background,
                    modifier = Modifier.size(30.dp), strokeWidth = 2.dp
                )
            } else {
                Text(
                    stringResource(R.string.submit),
                    style = MaterialTheme.typography.labelMedium
                )
            }

        }

        Spacer(Modifier.height(80.dp))
        Text(
            stringResource(R.string.dev_details),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )

    }
}



