package com.example.musicapplicationse114

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.musicapplicationse114.ui.screen.home.HomeScreen
import com.example.musicapplicationse114.ui.screen.login.LoginScreen
import com.example.musicapplicationse114.ui.screen.signUp.SignUpScreen
import com.example.musicapplicationse114.ui.screen.start.StartScreen

sealed class Screen(val route: String, val title: String) {
        object Home : Screen("home", "Home")
        object Login : Screen("login", "Login")
        object SignUp : Screen("signup", "Sign Up")
        object Detail : Screen("detail", "Detail")
        object Start : Screen("start", "Start")
        object Search : Screen("search", "Search")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val mainViewModel : MainViewModel = hiltViewModel()
    val mainState = mainViewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(mainState.value.error) {
        if (mainState.value.error.isNotEmpty()) {
            Toast.makeText(context, mainState.value.error, Toast.LENGTH_SHORT).show()
            mainViewModel.setError("")
        }
    }

    NavHost(navController = navController, startDestination = Screen.Start.route)
    {
        composable(Screen.Start.route) {
            StartScreen(navController = navController, viewModel = hiltViewModel(), mainViewModel)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController, viewModel = hiltViewModel(), mainViewModel)
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(navController = navController, viewModel = hiltViewModel(), mainViewModel)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController, viewModel = hiltViewModel(), mainViewModel)
        }


    }
}