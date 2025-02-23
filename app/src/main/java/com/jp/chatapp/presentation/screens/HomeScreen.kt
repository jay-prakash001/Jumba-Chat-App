package com.jp.chatapp.presentation.screens

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.jp.chatapp.presentation.screens.homeScreens.ContactsPage
import com.jp.chatapp.presentation.screens.homeScreens.HomePage
import com.jp.chatapp.presentation.screens.homeScreens.ProfilePage
import com.jp.chatapp.presentation.viewmodel.HomeViewModel
import com.jp.chatapp.presentation.viewmodel.MainViewmodel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    homeViewModel: HomeViewModel = koinViewModel(), mainViewmodel: MainViewmodel
) {
    val pages = listOf(
        Screens("Home", Icons.Filled.Home, Icons.Outlined.Home),
        Screens("Contacts", Icons.Filled.AccountBox, Icons.Outlined.AccountBox),
        Screens("Profile", Icons.Filled.AccountCircle, Icons.Outlined.AccountCircle),
    )
    val pagerState = rememberPagerState { pages.size }
    var selectedPage by remember { mutableIntStateOf(0) }

    LaunchedEffect(selectedPage) {
        pagerState.animateScrollToPage(
            selectedPage,
            animationSpec = tween(durationMillis = 700, easing = LinearOutSlowInEasing)
        )

    }

    LaunchedEffect(pagerState.currentPage) {
//        if(!pagerState.isScrollInProgress){

        selectedPage = pagerState.currentPage
//        }
    }
    Scaffold(

        topBar = {
            TopAppBar(title = {
                Text(pages[selectedPage].title, style = MaterialTheme.typography.titleLarge)
            })
        },

        bottomBar = {
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
            pagerState,
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) { page ->
            when (page % pages.size) {
                0 -> HomePage(
                    navController = navController,
                    viewModel = homeViewModel,
                    mainViewmodel = mainViewmodel
                )

                1 -> ContactsPage(
                    viewModel = homeViewModel,
                    navController = navController,
                )

                2 -> ProfilePage(homeViewModel = homeViewModel, navController = navController)
            }

        }
    }

}

data class Screens(val title: String, val selectIcon: ImageVector, val unSelectedIcon: ImageVector)
