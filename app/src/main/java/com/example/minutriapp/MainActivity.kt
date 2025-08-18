package com.example.minutriapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.compose.rememberNavController
import com.example.minutriapp.ui.theme.MiNutriAppTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.minutriapp.ui.login.LoginScreen
import com.example.minutriapp.ui.register.RegisterScreen
import com.example.minutriapp.ui.recovery.RecoveryScreen
import com.example.minutriapp.ui.home.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiNutriAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "login"
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