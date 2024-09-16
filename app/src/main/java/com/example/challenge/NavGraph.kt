package com.example.challenge

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.challenge.ui.theme.ForgotPasswordScreen
import com.example.challenge.ui.theme.LoginScreen
import com.example.challenge.activities.RegisterScreen
import com.example.challenge.ui.theme.ResetPasswordScreen
import androidx.compose.material3.MaterialTheme

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onForgotPasswordClick = { navController.navigate("forgot_password") },
                onRegisterClick = { navController.navigate("register") }
            )
        }
        composable("register") {
            RegisterScreen(onRegisterSuccess = { navController.popBackStack() })
        }
        composable("forgot_password") {
            ForgotPasswordScreen(
                onBackToLoginClick = { navController.popBackStack() },
                navController = navController // Passar o navController
            )
        }
        composable(
            route = "reset_password/{token}",
            arguments = listOf(navArgument("token") { type = NavType.StringType })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            ResetPasswordScreen(
                onPasswordResetSuccess = { navController.popBackStack() },
                token = token // Passar o token para a ResetPasswordScreen
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppNavigation() {
    MaterialTheme {
        AppNavigation()
    }
}
