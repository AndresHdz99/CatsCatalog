package org.catsproject.project.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch
import org.catsproject.project.ui.navigation.screen.DesktopHomeScreen
import org.catsproject.project.ui.navigation.screen.FavoriteScreenDesktop
import org.catsproject.project.ui.navigation.screen.LoginScreen
import org.catsproject.project.ui.view.ModalDrawer
import org.catsproject.project.ui.view.TopBar
import org.catsproject.project.ui.viewmodel.CatsSearchViewModel
import org.catsproject.project.ui.viewmodel.LoginViewModel
import org.koin.compose.getKoin
import org.koin.compose.koinInject
import java.util.UUID


@Composable
fun NavigateDesktopWrapper(loginViewModel: LoginViewModel = koinInject<LoginViewModel>()){
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val isLogin = navBackStackEntry?.destination?.route == LoginScreen::class.qualifiedName





    ModalDrawer(
        drawerState,
        content = {

            Column {

                if (!isLogin) {
                    TopBar {
                        scope.launch { drawerState.open() }
                    }
                }

                NavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    startDestination = LoginScreen
                ){

                    composable<LoginScreen> {
                        LoginScreen() {
                            navController.navigate(DesktopHomeScreen)
                        }
                    }

                    composable<DesktopHomeScreen> {
                        DesktopHomeScreen()

                    }

                    composable<FavoriteScreenDesktop> {
                        FavoriteScreenDesktop()
                    }

                }
            }

        },
        logOut = {

            loginViewModel.logout()
            navController.navigate(LoginScreen){
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }

            scope.launch {
                drawerState.close()
            }
        },
        favorite = {
            navController.navigate(FavoriteScreenDesktop)
            scope.launch {
                drawerState.close()
            }
        },
        catalog = {

            scope.launch {
                navController.popBackStack()
                drawerState.close()
            }
        }
    )




}