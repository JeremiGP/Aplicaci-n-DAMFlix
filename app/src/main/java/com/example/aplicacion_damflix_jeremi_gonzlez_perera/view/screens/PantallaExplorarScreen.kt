package com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.R
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel.ExplorarUiState
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel.ExplorarViewModel

@Composable
fun PantallaExplorarScreen(
    viewModel: ExplorarViewModel,
    onPeliculaImportada: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var textoBusqueda by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = {
                textoBusqueda = it
                viewModel.buscarOnline(it)
            },
            label = { Text(stringResource(R.string.label_buscar_api)) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        when (val state = uiState) {
            is ExplorarUiState.Cargando -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is ExplorarUiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = state.mensajeResId),
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = { viewModel.cargarPeliculasOnline() }) {
                        Text(text = stringResource(id = R.string.btn_reintentar))
                    }
                }
            }
            is ExplorarUiState.Exito -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.peliculas) { pelicula ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = pelicula.titulo, style = MaterialTheme.typography.titleMedium)
                                    Text(
                                        text = "${pelicula.director} • ${pelicula.anio}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                Button(onClick = {
                                    viewModel.importarAFirestore(pelicula)
                                    onPeliculaImportada()
                                }) {
                                    Text(text = stringResource(id = R.string.btn_importar_nube))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
