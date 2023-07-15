package com.lifting.app.navigation.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lifting.app.navigation.MainScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph(navController: NavHostController, startDestination: String) {


    LaunchedEffect(key1 = startDestination) {
        if (startDestination == Graph.HOME) {
            navController.navigate(Graph.HOME){
                navController.popBackStack()
            }
        } else if (startDestination == AuthScreen.SignInScreen.route) {
            navController.navigate(AuthScreen.SignInScreen.route) {
                popUpTo(AuthScreen.OnBoardingScreen.route) {
                    inclusive = true
                }
            }
        } else navController.navigate(AuthScreen.OnBoardingScreen.route)
    }

    NavHost(navController = navController, startDestination =  Graph.AUTHENTICATION, route = Graph.ROOT) {
        authNavGraph(navController = navController)
        composable(route = Graph.HOME){
            MainScreen()
        }
    }
}