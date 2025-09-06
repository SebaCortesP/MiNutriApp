package com.example.minutriapp.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.minutriapp.ui.theme.Primario
import com.example.minutriapp.ui.theme.Secundario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBar(
    currentRoute: String,
    title: String,
    onNavigateTo: (String) -> Unit,
    onLogout: () -> Unit,
    userName: String? = null
) {
    TopAppBar(
        title = {
            Text(
                text = if (userName != null && currentRoute == "home") {
                    "Bienvenido $userName"
                } else {
                    title
                },
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            when (currentRoute) {
                "login", "register", "recovery" -> {
                    var expanded by remember { mutableStateOf(false) }
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú", tint = Color.White)
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(text = { Text("Login") }, onClick = {
                            expanded = false
                            onNavigateTo("login")
                        })
                        DropdownMenuItem(text = { Text("Register") }, onClick = {
                            expanded = false
                            onNavigateTo("register")
                        })
                        DropdownMenuItem(text = { Text("Recovery") }, onClick = {
                            expanded = false
                            onNavigateTo("recovery")
                        })
                    }
                }
                "home" -> {
                    var expanded by remember { mutableStateOf(false) }
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú", tint = Color.White)
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(text = { Text("Home") }, onClick = {
                            expanded = false
                            onNavigateTo("home")
                        })
                        DropdownMenuItem(text = { Text("Perfil") }, onClick = {
                            expanded = false
                            onNavigateTo("profile")
                        })
                        DropdownMenuItem(text = { Text("Cerrar sesión") }, onClick = {
                            expanded = false
                            onLogout()
                        })
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Primario)
    )
}


