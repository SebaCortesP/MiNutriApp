package com.example.minutriapp.ui.recovery

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.minutriapp.R
import com.example.minutriapp.ui.theme.BodySize
import com.example.minutriapp.ui.theme.Cuarto
import com.example.minutriapp.ui.theme.SubtitleSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoveryScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }
    val azulApp = Color(0xFF243B94)

    Scaffold(
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.nutri_logo),
                contentDescription = "Logo App",
                modifier = Modifier.size(150.dp)
            )
            // Input Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Mostrar errores
            if (errorMessage.isNotEmpty()) {
                Text(errorMessage, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(8.dp))
            }

            // btn confirmar
            Button(
                onClick = {
                    if (email.isBlank()) {
                        errorMessage = "Es obligatorio ingresar su correo"
                    }else {
                        errorMessage = ""
                        // volver al login
                        showSuccessDialog = true
                        // navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Cuarto)
            ) {
                Text("Confirmar", color = Color.White, fontSize = SubtitleSize)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón Volver
            TextButton(onClick = { navController.popBackStack() }) {
                Text("Volver", color = Cuarto, fontSize = BodySize)
            }
        }

    }
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Proceso de recuperación de contraseña") },
            text = { Text("Hemos enviado a su correo un mensaje con un link para proceder con la recuperación de sus credenciales.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}
