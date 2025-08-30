package com.example.minutriapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.minutriapp.ui.login.LoginScreen
import com.example.minutriapp.ui.register.RegisterScreen
import com.example.minutriapp.ui.recovery.RecoveryScreen
import com.example.minutriapp.ui.home.HomeScreen
import com.example.minutriapp.ui.theme.MiNutriAppTheme
import com.example.minutriapp.ui.components.TopNavBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiNutriAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    // detectar en qué pantalla estoy
                    val navBackStackEntry = navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry.value?.destination?.route ?: "login"
                    val screenTitles = mapOf(
                        "login" to "Iniciar Sesión",
                        "register" to "Registrar Usuario",
                        "recovery" to "Recuperar Contraseña",
                        "home" to "El libro de Sanji"
                    )
                    val title = screenTitles[currentRoute] ?: "Mi NutriApp"
                    Scaffold(
                        topBar = {
                            TopNavBar(
                                currentRoute = currentRoute,
                                title = title,
                                onNavigateTo = { route ->
                                    navController.navigate(route) {
                                        // evitar duplicados en el stack
                                        launchSingleTop = true
                                    }
                                },
                                onLogout = {
                                    // volver a login limpiando el backstack
                                    navController.navigate("login") {
                                        popUpTo("home") { inclusive = true }
                                    }
                                }
                            )
                        }
                    ) { innerPadding: PaddingValues ->
                        NavHost(
                            navController = navController,
                            startDestination = "login",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable("login") { LoginScreen(navController) }
                            composable("register") { RegisterScreen(navController) }
                            composable("recovery") { RecoveryScreen(navController) }
                            composable("home") { HomeScreen(navController) }
                        }
                    }
                }
            }
        }
    }
}
