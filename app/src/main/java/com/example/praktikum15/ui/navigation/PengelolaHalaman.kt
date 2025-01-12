package com.example.praktikum15.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import com.example.praktikum15.ui.view.HomeScreen
import com.example.praktikum15.ui.view.InsertMhsView

@Composable
fun PengelolaHalaman(
    modifier: Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost (
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ) {
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsert.route)
                },
            )
        }

        composable(DestinasiInsert.route){
            InsertMhsView(
                onBack = {navController.popBackStack()},
                onNavigate = {
                    navController.navigate(DestinasiHome.route)
                }
            )
        }

        composable(DestinasiEdit.route) { backStackEntry ->
            val nim = backStackEntry.arguments?.getString("nim") ?: ""
            InsertMhsView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                initialNim = nim
            )
        }
    }
}