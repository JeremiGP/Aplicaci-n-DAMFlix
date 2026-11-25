package com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.R
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel.FirestorePeliculaViewModel

@Composable
fun DetallePeliculaCloudScreen(
    peliculaId: String,
    viewModel: FirestorePeliculaViewModel,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(peliculaId) {
        viewModel.cargarPeliculaPorId(peliculaId)
    }

    val pelicula by viewModel.peliculaSeleccionada.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = onVolver) {
            Text(text = stringResource(R.string.btn_volver))
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (pelicula == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = pelicula!!.titulo, style = MaterialTheme.typography.headlineMedium)
                    Text(text = stringResource(R.string.detalle_director, pelicula!!.director))
                    Text(text = stringResource(R.string.detalle_anio, pelicula!!.anio))
                    Text(
                        text = stringResource(
                            id = if (pelicula!!.esVista) R.string.estado_vista else R.string.estado_pendiente
                        )
                    )
                }
            }
        }
    }
}
