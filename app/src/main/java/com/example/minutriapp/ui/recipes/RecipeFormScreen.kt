package com.example.minutriapp.ui.recipes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.text.input.KeyboardType
import com.example.minutriapp.ui.home.Recipe
import android.util.Log
import com.example.minutriapp.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeFormScreen(
    navController: NavController,
    recipes: MutableList<Recipe>,
    currentUser: User?
) {
    var name by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var carbs by remember { mutableStateOf("") }
    var fat by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("")}
    var showSuccessDialog by remember { mutableStateOf(false) }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Nueva Receta", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            // Campos de formulario...
            OutlinedTextField( value = name, onValueChange = { name = it }, label = { Text("Nombre") }, singleLine = true, modifier = Modifier.fillMaxWidth() )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField( value = calories, onValueChange = { calories = it }, label = { Text("Calorías") }, singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth() )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField( value = protein, onValueChange = { protein = it }, label = { Text("Proteínas (g)") }, singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth() )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField( value = carbs, onValueChange = { carbs = it }, label = { Text("Carbohidratos (g)") }, singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth() )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField( value = fat, onValueChange = { fat = it }, label = { Text("Grasas (g)") }, singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth() )
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    // Crear nueva receta y añadir a la lista en memoria
                    recipes.add(
                        Recipe(
                            name = name,
                            calories = calories.toIntOrNull() ?: 0,
                            protein = protein.toIntOrNull() ?: 0,
                            carbs = carbs.toIntOrNull() ?: 0,
                            fat = fat.toIntOrNull() ?: 0,
                            author = currentUser?.name ?: "Sin autor"
                        )
                    )
                    showSuccessDialog = true
                    Log.d("RecipesDebug", "Receta añadida: $recipes")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Receta")
            }
        }

        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showSuccessDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        showSuccessDialog = false
                        navController.popBackStack()
                    }) {
                        Text("OK")
                    }
                },
                title = { Text("Receta guardada") },
                text = { Text("Tu receta ha sido creada con éxito.") }
            )
        }
    }
}
