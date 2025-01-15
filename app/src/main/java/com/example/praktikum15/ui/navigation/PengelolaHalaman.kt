package com.example.praktikum15.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import com.example.praktikum15.ui.view.DetailMhsView
import com.example.praktikum15.ui.view.EditMhsView
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
                onDetailClick = {nim ->
                    navController.navigate("detail/${nim}")
                }
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

        composable("detail/{nim}") { backStackEntry ->
            val nim = backStackEntry.arguments?.getString("nim") ?: ""
            DetailMhsView(
                nim = nim,
                navigateBack = { navController.popBackStack() },
                onUpdateClick = { navController.navigate(DestinasiInsert.route)}
            )
        }
    }
}