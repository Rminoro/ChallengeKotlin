// NavGraph.kt
package com.example.challenge.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.challenge.ui.theme.ForgotPasswordScreen
import com.example.challenge.ui.theme.LoginScreen
import com.example.challenge.activities.RegisterScreen
import com.example.challenge.ui.theme.ResetPasswordScreen
import com.example.challenge.activities.CrudActivity

fun NavGraphBuilder.addNavGraph(navController: NavHostController) {
    composable("login") {
        LoginScreen(
            onForgotPasswordClick = { navController.navigate("forgot_password") },
            onRegisterClick = { navController.navigate("register") },
            onCrudClick = { navController.navigate("crud") }
        )
    }
    composable("register") {
        RegisterScreen(onRegisterSuccess = { navController.popBackStack() })
    }
    composable("forgot_password") {
        ForgotPasswordScreen(
            onBackToLoginClick = { navController.popBackStack() },
            navController = navController
        )
    }
    composable(
        route = "reset_password/{token}",
        arguments = listOf(navArgument("token") { type = NavType.StringType })
    ) { backStackEntry ->
        val token = backStackEntry.arguments?.getString("token") ?: ""
        ResetPasswordScreen(
            onPasswordResetSuccess = { navController.popBackStack() },
            token = token
        )
    }
    composable("crud") {
        CrudActivity() // Chama a tela CRUD
    }
}
