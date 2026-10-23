package com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.Pelicula
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.ui.theme.Aplicacion_DAMFlix_Jeremi_González_PereraTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioPeliculaScreen(
    onPeliculaAdded: (Pelicula) -> Unit,
    onNavigateBack: () -> Unit,
) {
    var titulo by remember { mutableStateOf("") }
    var director by remember { mutableStateOf("") }
    var fechaEstreno by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var pendiente by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Añadir Película") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título de la Película") },
                modifier = Modifier.fillMaxWidth(),
            )

            OutlinedTextField(
                value = director,
                onValueChange = { director = it },
                label = { Text("Director de la Película") },
                modifier = Modifier.fillMaxWidth(),
            )

            OutlinedTextField(
                value = fechaEstreno,
                onValueChange = { fechaEstreno = it },
                label = { Text("Fecha de Estreno") },
                modifier = Modifier.fillMaxWidth(),
            )

            OutlinedTextField(
                value = genero,
                onValueChange = { genero = it },
                label = { Text("Género de la Película") },
                modifier = Modifier.fillMaxWidth(),
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = pendiente,
                    onCheckedChange = { pendiente = it },
                )
                Text(text = "Pendiente")
            }

            Button(
                onClick = {
                    val nuevaPelicula = Pelicula(
                        id = System.currentTimeMillis().toInt(),
                        titulo = titulo,
                        director = director,
                        fechaEstreno = fechaEstreno.ifBlank { "Desconocida" },
                        genero = genero.ifBlank { "General" },
                        pendiente = pendiente,
                        esFavorita = false,
                    )
                    onPeliculaAdded(nuevaPelicula)
                    Toast.makeText(context, "Película guardada: $titulo", Toast.LENGTH_SHORT).show()
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = titulo.isNotBlank() && director.isNotBlank(),
            ) {
                Text(text = "Guardar Película")
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormularioPeliculaScreenPreview() {
    Aplicacion_DAMFlix_Jeremi_González_PereraTheme {
        FormularioPeliculaScreen(
            onPeliculaAdded = {},
            onNavigateBack = {},
        )
    }
}
