package com.example.minutriapp.ui.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.minutriapp.ui.theme.BodySize
import com.example.minutriapp.ui.theme.Cuarto
import com.example.minutriapp.ui.theme.SubtitleSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repetirPassword by remember { mutableStateOf("") }

    // Estados de error
    var emailError by remember { mutableStateOf(false) }
    var nombreError by remember { mutableStateOf(false) }
    var apellidoError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var generalError by remember { mutableStateOf("") }

    // Estado para modal
    var showSuccessDialog by remember { mutableStateOf(false) }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = false
                },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                isError = emailError
            )
            if (emailError) {
                Text("Ingrese un email válido", color = Color.Red, fontSize = BodySize)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                isError = nombreError
            )
            if (nombreError) {
                Text("Nombre es obligatorio", color = Color.Red, fontSize = BodySize)
            }
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                isError = apellidoError
            )
            if (apellidoError) {
                Text("Apellido es obligatorio", color = Color.Red, fontSize = BodySize)
            }
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = false
                },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                isError = passwordError
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = repetirPassword,
                onValueChange = {
                    repetirPassword = it
                    passwordError = false
                },
                label = { Text("Repetir contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                isError = passwordError
            )
            if (passwordError) {
                Text("Las contraseñas no coinciden", color = Color.Red, fontSize = BodySize)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón Registro
            Button(
                onClick = {
                    // Reset errores
                    emailError = false
                    passwordError = false
                    nombreError = false
                    apellidoError = false
                    generalError = ""

                    // Validaciones
                    var hasError = false

                    if (email.isBlank() || !email.contains("@")) {
                        emailError = true
                        hasError = true
                    }
                    if (nombre.isBlank()) {
                        nombreError = true
                        hasError = true
                    }
                    if (apellido.isBlank()) {
                        apellidoError = true
                        hasError = true
                    }
                    if (password.length < 1 || password != repetirPassword) {
                        passwordError = true
                        hasError = true
                    }

                    if (!hasError) {
                        // Todo correcto → mostrar modal
                        showSuccessDialog = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Cuarto)
            ) {
                Text("Registrarse", color = Color.White, fontSize = SubtitleSize)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Volver / Iniciar sesión
            TextButton(onClick = { navController.popBackStack() }) {
                Text("Ya tengo cuenta, iniciar sesión", color = Cuarto, fontSize = BodySize)
            }
        }

        // Modal de éxito
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showSuccessDialog = false },
                title = { Text("Registro exitoso") },
                text = { Text("Tu cuenta ha sido creada con éxito.") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showSuccessDialog = false
                            navController.navigate("home") {
                                popUpTo("register") { inclusive = true }
                            }
                        }
                    ) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
}
