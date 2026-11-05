package com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.R
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel.PeliculaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioPeliculaScreen(
    viewModel: PeliculaViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var titulo by remember { mutableStateOf("") }
    var director by remember { mutableStateOf("") }
    var fechaEstreno by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var pendiente by remember { mutableStateOf(false) }
    var esVista by remember { mutableStateOf(false) }

    val esFormularioValido = titulo.isNotBlank() && director.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.anadir_pelicula)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text(stringResource(R.string.titulo_pelicula)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = director,
                onValueChange = { director = it },
                label = { Text(stringResource(R.string.director_pelicula)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = fechaEstreno,
                onValueChange = { fechaEstreno = it },
                label = { Text(stringResource(R.string.fecha_estreno)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = genero,
                onValueChange = { genero = it },
                label = { Text(stringResource(R.string.genero_pelicula)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(checked = pendiente, onCheckedChange = { pendiente = it })
                Text(text = stringResource(R.string.pendiente))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(checked = esVista, onCheckedChange = { esVista = it })
                Text(text = stringResource(R.string.ya_vista))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.agregarPelicula(
                        titulo = titulo,
                        director = director,
                        fechaEstreno = fechaEstreno.ifBlank { "Desconocida" },
                        genero = genero.ifBlank { "General" },
                        pendiente = pendiente,
                        esVista = esVista
                    )
                    onNavigateBack()
                },
                enabled = esFormularioValido,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = stringResource(R.string.btn_accept))
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
