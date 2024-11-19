package com.example.chaton

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chaton.feature.auth.chat.ChatScreen
import com.example.chaton.feature.auth.home.HomeScreen
import com.example.chaton.feature.auth.signin.SignInScreen
import com.example.chaton.feature.auth.signup.SignUpScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainApp() {

    Surface(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()

        val startDestination = FirebaseAuth.getInstance().currentUser?.let { "home" } ?: "login"

        NavHost(navController = navController, startDestination = startDestination) {
            composable("login") {
                SignInScreen(navController = navController)
            }

            composable("signup") {
                SignUpScreen(navController = navController)
            }

            composable("home") {
                HomeScreen(navController = navController)
            }

            composable("chat/{channelId}", arguments = listOf(
                navArgument("channelId") {
                    type = NavType.StringType
                }
            )) {
                ChatScreen(
                    navController = navController,
                    channelId = it.arguments?.getString("channelId") ?: ""
                )
            }
        }
    }
}