package com.lifting.app.navigation.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lifting.app.navigation.MainScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph(navController: NavHostController) {

    //Yeni oluşturduğun splash viewmodeldeki kodu eski splashviewmodelde dene. mainactivityden parametre gönder. burada launchedeffect kullan.

    NavHost(navController = navController, startDestination =  Graph.AUTHENTICATION, route = Graph.ROOT) {
        authNavGraph(navController = navController)
        composable(route = Graph.HOME){
            MainScreen()
        }
    }
}