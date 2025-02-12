package com.jp.chatapp.presentation.screens.auth

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.jp.chatapp.presentation.navigation.Home
import com.jp.chatapp.presentation.navigation.HomeRoute
import com.jp.chatapp.presentation.utils.getFileBytesFromUri
import com.jp.chatapp.presentation.viewmodel.HomeViewModel
import com.jp.chatapp.presentation.viewmodel.LoginViewmodel
import com.jp.chatapp.presentation.viewmodel.MainViewmodel
import com.jp.chatapp.utils.ACCESS_TOKEN
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: LoginViewmodel = koinViewModel(),
    mainViewmodel: MainViewmodel, homeViewModel: HomeViewModel
) {
    val phone = viewModel.phone.collectAsStateWithLifecycle()

    val user = viewModel.user.collectAsStateWithLifecycle()
    var showLoginDialog by remember { mutableStateOf(false) }
    LaunchedEffect(user.value) {
        if (user.value.data != null) {
            showLoginDialog = true

        }
    }
    val context = LocalContext.current

    AnimatedVisibility(showLoginDialog) {

        AlertDialog(onDismissRequest = {
            Toast.makeText(context, "Press on Go to Home", Toast.LENGTH_SHORT).show()
        }, confirmButton = {
            Button(
                onClick = {
                    mainViewmodel.connect()

                    navController.navigate(Home) {
                        popUpTo(0)
                    }


                },
            ) {
                Text("Go to Home")
            }
        },
            title = {
                Text("Logged In Successfully", style = MaterialTheme.typography.displayLarge)
            },
            tonalElevation = 20.dp,
            icon = {
                Icon(Icons.Default.Home, contentDescription = null)
            }, text = {
                Text(
                    "Your account has fetched successfully.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        )
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
        ElevatedButton(
            onClick = {

                viewModel.login()


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
                    stringResource(R.string.login),
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



