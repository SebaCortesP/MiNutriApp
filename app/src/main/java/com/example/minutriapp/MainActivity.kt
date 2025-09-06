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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.example.minutriapp.ui.home.Recipe
import com.example.minutriapp.ui.home.parseRecipesFromJson
import com.example.minutriapp.ui.recipes.RecipeFormScreen

data class User(
    val email: String,
    val name: String
)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val recipes = remember { mutableStateListOf<Recipe>() }
            var currentUser by remember { mutableStateOf<User?>(null) }
            // cargar iniciales una sola vez
            LaunchedEffect(Unit) {
                if (recipes.isEmpty()) {
                    recipes.addAll(parseRecipesFromJson("""[
              {"name": "Ensalada César", "calories": 320, "protein": 15, "carbs": 25, "fat": 18, "author" : "Usuario Test Uno"},
              {"name": "Pollo al horno", "calories": 450, "protein": 40, "carbs": 10, "fat": 25, "author" : "Usuario Test Dos"},
              {"name": "Smoothie de frutas", "calories": 200, "protein": 5, "carbs": 45, "fat": 2, "author" : "Usuario Test Uno"},
              {"name": "Pasta boloñesa", "calories": 600, "protein": 30, "carbs": 70, "fat": 20, "author" : "Usuario Test Tres" },
              {"name": "Sopa de verduras", "calories": 150, "protein": 6, "carbs": 20, "fat": 3, "author" : "Usuario Test Tres"}
            ]"""))
                }
            }
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
                                },
                                userName = currentUser?.name
                            )
                        }
                    ) { innerPadding: PaddingValues ->
                        NavHost(
                            navController = navController,
                            startDestination = if (currentUser == null) "login" else "home",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable("login") {
                                LoginScreen(
                                    navController,
                                    onLogin = { email ->
                                        currentUser = User(email, "Usuario Uno")
                                        navController.navigate("home") {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    },
                                    onNavigateRegister = { navController.navigate("register") },
                                    onNavigateRecovery = { navController.navigate("recovery") }
                                )
                            }
                            composable("register") { RegisterScreen(navController) }
                            composable("recovery") { RecoveryScreen(navController) }
                            composable("home") {
                                currentUser?.let { user ->
                                    HomeScreen(navController, recipes, user)
                                } ?: run {
                                    navController.navigate("login") {
                                        popUpTo("home") { inclusive = true }
                                    }
                                }
                            }
                            composable ( "createRecipe" ) { RecipeFormScreen(navController, recipes, currentUser!!) }                        }
                    }
                }
            }
        }
    }
}
