package com.jp.chatapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jp.chatapp.presentation.screens.auth.LoginPage
import com.jp.chatapp.presentation.screens.auth.RegisterPage
import com.jp.chatapp.presentation.viewmodel.HomeViewModel
import com.jp.chatapp.presentation.viewmodel.LoginViewmodel
import com.jp.chatapp.presentation.viewmodel.MainViewmodel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewmodel = koinViewModel(),
    mainViewmodel: MainViewmodel, homeViewModel: HomeViewModel
) {
    val pages = listOf("Login", "Register")

    val pagerState = rememberPagerState { pages.size }
    var selectedPage by remember {
        mutableIntStateOf(pagerState.currentPage)
    }

    LaunchedEffect(selectedPage) {
        pagerState.animateScrollToPage(selectedPage)

    }
    LaunchedEffect(pagerState.currentPage) {
        selectedPage = pagerState.currentPage
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        TabRow(
            selectedPage,
            modifier = Modifier
                .fillMaxWidth(.8f)
                .height(60.dp)
                .clip(RoundedCornerShape(20.dp)),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            divider = {}, indicator = {}
        ) {

            pages.forEachIndexed { index, s ->

                Tab(
                    selectedPage == index,
                    onClick = {
                        selectedPage = index
                    },
                    selectedContentColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = s,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(if (selectedPage == index) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.primaryContainer)
                            .padding(horizontal = 40.dp, vertical = 20.dp),
                        textAlign = TextAlign.Center
                    )

                }
            }


        }
        HorizontalPager(pagerState, modifier = Modifier.fillMaxSize()) { page: Int ->
            when (page) {
                0 -> {
                    LoginPage(
                        navController = navController,
                        mainViewmodel = mainViewmodel,
                        viewModel = viewModel, homeViewModel = homeViewModel
                    )
                }
                else ->{

                    RegisterPage(
                        navController = navController,
                        mainViewmodel = mainViewmodel,
                        viewModel = viewModel
                    )
                }
            }
        }


    }

}

