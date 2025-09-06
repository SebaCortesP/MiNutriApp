package com.example.minutriapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.minutriapp.User
import org.json.JSONArray

data class Recipe(
    val name: String,
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fat: Int,
    val author: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    recipes: List<Recipe>,
    currentUser: User?
) {
    // Carga inicial desde JSON
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("createRecipe") },
                containerColor = Color(0xFF243B94),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Menu, contentDescription = "Nueva Receta")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            //Text("Bienvenido a MinutriApp", style = MaterialTheme.typography.headlineMedium)
            Text(
                text = "Bienvenido ${currentUser?.name ?: "Invitado"}",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(recipes) { recipe ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(recipe.name, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Calorías: ${recipe.calories}")
                            Text("Proteínas: ${recipe.protein} g")
                            Text("Carbohidratos: ${recipe.carbs} g")
                            Text("Grasas: ${recipe.fat} g")
                            Text("Autor: ${recipe.author}")
                        }
                    }
                }
            }
        }
    }
}


fun parseRecipesFromJson(json: String): List<Recipe> {
    val jsonArray = JSONArray(json)
    val recipes = mutableListOf<Recipe>()
    for (i in 0 until jsonArray.length()) {
        val obj = jsonArray.getJSONObject(i)
        recipes.add(
            Recipe(
                name = obj.getString("name"),
                calories = obj.getInt("calories"),
                protein = obj.getInt("protein"),
                carbs = obj.getInt("carbs"),
                fat = obj.getInt("fat"),
                author = obj.getString("author"),
            )
        )
    }
    return recipes
}