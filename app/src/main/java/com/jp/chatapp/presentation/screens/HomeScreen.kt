package com.jp.chatapp.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.jp.chatapp.presentation.screens.homeScreens.ContactsPage
import com.jp.chatapp.presentation.screens.homeScreens.HomePage
import com.jp.chatapp.presentation.screens.homeScreens.SettingsPage
import com.jp.chatapp.presentation.viewmodel.HomeViewModel
import com.jp.chatapp.presentation.viewmodel.MainViewmodel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewmodel: HomeViewModel = koinViewModel(), mainViewmodel: MainViewmodel, token: String
) {
    val pages = listOf(
        Screens("Home Page", Icons.Filled.Home, Icons.Outlined.Home),
        Screens("Settings Page", Icons.Filled.Settings, Icons.Outlined.Settings),
        Screens("Contacts Page", Icons.Filled.AccountBox, Icons.Outlined.AccountBox),
    )
    val pagerState = rememberPagerState { pages.size }
    var selectedPage by remember { mutableIntStateOf(0) }
    LaunchedEffect(true) {
        if (token != null) {
            mainViewmodel.joinSocket()
            viewmodel.getChatList(token)

        }

        println("joined ................ $token")
    }
    LaunchedEffect(selectedPage) {
        pagerState.animateScrollToPage(selectedPage)

    }
    LaunchedEffect(pagerState.currentPage) {
        selectedPage = pagerState.currentPage
    }
    Scaffold(bottomBar = {
        NavigationBar {
            pages.forEachIndexed { index, screens ->
                NavigationBarItem(index == selectedPage, onClick = {
                    selectedPage = index
                }, icon = {
                    Icon(
                        if (index == selectedPage) screens.selectIcon else screens.unSelectedIcon,
                        contentDescription = screens.title
                    )
                }, label = { Text(screens.title) })
            }
        }
    }) {

        HorizontalPager(
            pagerState, modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) { page ->
            when (page % pages.size) {
                0 -> HomePage(
                    navController = navController,
                    viewModel = viewmodel, token = token,
                    mainViewmodel = mainViewmodel
                )

                1 -> SettingsPage()
                2 -> ContactsPage(
                    viewModel = viewmodel,
                    navController = navController,
                    token = token
                )
            }

        }
    }

}

data class Screens(val title: String, val selectIcon: ImageVector, val unSelectedIcon: ImageVector)
