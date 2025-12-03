package mushtaq.projekt.gui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import mushtaq.projekt.gui.theme.home.HomeScreen

@Composable
fun AppNavGraph(navController: NavHostController){
    //define "home" as starting screen
    NavHost(navController = navController, startDestination = "home"){
        composable("home"){
            HomeScreen(navController)
        }
        //
    }
}