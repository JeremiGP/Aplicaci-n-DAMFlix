package com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.R
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel.FirestorePeliculaViewModel

@Composable
fun FormularioPeliculaCloudScreen(
    peliculaId: String = "",
    viewModel: FirestorePeliculaViewModel,
    onOperacionCompletada: () -> Unit,
    modifier: Modifier = Modifier
) {
    val peliculaExistente by viewModel.peliculaSeleccionada.collectAsStateWithLifecycle()
    val esModoEdicion = peliculaId.isNotBlank()

    LaunchedEffect(peliculaId) {
        if (esModoEdicion) {
            viewModel.cargarPeliculaPorId(peliculaId)
        } else {
            viewModel.limpiarSeleccion()
        }
    }

    var titulo by remember { mutableStateOf("") }
    var director by remember { mutableStateOf("") }
    var anioTexto by remember { mutableStateOf("") }
    var esVista by remember { mutableStateOf(false) }

    LaunchedEffect(peliculaExistente) {
        peliculaExistente?.let { p ->
            if (esModoEdicion) {
                titulo = p.titulo
                director = p.director
                anioTexto = p.anio.toString()
                esVista = p.esVista
            }
        }
    }

    val esFormularioValido = titulo.isNotBlank() &&
            director.isNotBlank() &&
            anioTexto.toIntOrNull() != null

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(
                id = if (esModoEdicion) R.string.titulo_editar_pelicula else R.string.titulo_nueva_pelicula
            ),
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text(stringResource(R.string.label_titulo)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = director,
            onValueChange = { director = it },
            label = { Text(stringResource(R.string.label_director)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = anioTexto,
            onValueChange = { anioTexto = it },
            label = { Text(stringResource(R.string.label_anio)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = esVista, onCheckedChange = { esVista = it })
            Text(text = stringResource(R.string.label_ya_vista))
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                val anio = anioTexto.toIntOrNull() ?: 0
                if (esModoEdicion && peliculaExistente != null) {
                    viewModel.editarPelicula(
                        peliculaExistente!!.copy(
                            titulo = titulo,
                            director = director,
                            anio = anio,
                            esVista = esVista
                        )
                    )
                } else {
                    viewModel.crearPelicula(
                        titulo = titulo,
                        director = director,
                        anio = anio,
                        esVista = esVista
                    )
                }
                onOperacionCompletada()
            },
            enabled = esFormularioValido,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(
                    id = if (esModoEdicion) R.string.btn_actualizar else R.string.btn_guardar
                )
            )
        }
    }
}
