package org.catsproject.project.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.coroutines.launch
import org.catsproject.project.ui.navigation.screen.FavoriteScreen
import org.catsproject.project.ui.navigation.screen.InformationCart
import org.catsproject.project.ui.navigation.screen.LoginScreen
import org.catsproject.project.ui.navigation.screen.PullRefreshView
import org.catsproject.project.ui.view.ModalDrawer
import org.catsproject.project.ui.view.TopBar
import org.catsproject.project.ui.viewmodel.LoginViewModel
import org.koin.compose.koinInject


@Composable
fun NavigationWrapper(loginViewModel: LoginViewModel = koinInject<LoginViewModel>()){
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isLogin = currentDestination?.route == LoginScreen::class.qualifiedName
    val isInformation = currentDestination?.route?.startsWith(InformationScreen::class.qualifiedName?: "") == true





    Scaffold{ padding ->

        ModalDrawer(drawerState,Modifier.background(Color.White).padding(padding), {

            Column {

                if (!isLogin && !isInformation){
                    TopBar() {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                }



                NavHost(
                    navController = navController,
                    startDestination = LoginScreen
                ) {

                    composable<LoginScreen> {
                        LoginScreen() {
                            navController.navigate(CatalogScreen)
                        }
                    }
                    composable<CatalogScreen> {
                        PullRefreshView() {
                            navController.navigate(InformationScreen(it,NavigationEnum.CATALOG))
                        }
                    }

                    composable<InformationScreen> { navStack ->
                        val detail = navStack.toRoute<InformationScreen>()
                        InformationCart(detail.id,detail.navigation){
                            navController.popBackStack()
                        }
                    }

                    composable<FavoriteScreen> {
                        FavoriteScreen() {
                            navController.navigate(InformationScreen(it,NavigationEnum.FAVORITE))
                        }
                    }

                }



            }

        }, {
            loginViewModel.logout()
            navController.navigateUp()
            scope.launch {
                drawerState.close()
            }
        }, {
            scope.launch {
                drawerState.close()
            }
            navController.navigate(FavoriteScreen)
        },{
           navController.popBackStack()
            scope.launch {
                drawerState.close()
            }
        })


    }



}

