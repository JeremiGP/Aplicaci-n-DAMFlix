package com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens

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
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel.DetallePeliculaViewModel
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel.DetalleUiState

/**
 * Pantalla que muestra el detalle de una película consultada asíncronamente en Room.
 *
 * Utiliza [LaunchedEffect] para iniciar la búsqueda en cuanto se monta la pantalla.
 * Renderiza dinámicamente según el estado de la UI ([DetalleUiState]): Cargando, Exito o Error.
 *
 * @param peliculaId ID de la película a mostrar recibido mediante argumentos de navegación.
 * @param viewModel Instancia del [DetallePeliculaViewModel].
 * @param onVolver Callback para regresar a la pantalla del catálogo.
 * @param modifier Modificador para Compose.
 */
@Composable
fun PantallaDetalleScreen(
    peliculaId: Int,
    viewModel: DetallePeliculaViewModel,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Dispara la consulta asíncrona únicamente cuando la pantalla inicia o cambia el peliculaId
    LaunchedEffect(peliculaId) {
        viewModel.cargarDetallePelicula(peliculaId)
    }

    // Recoge el estado de la UI expuesto por el ViewModel respetando el ciclo de vida
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Botón de acción para volver al catálogo
        Button(onClick = onVolver) {
            Text(text = stringResource(id = R.string.btn_volver_catalogo))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Renderizado reactivo según el estado de la UI
        when (val state = uiState) {
            is DetalleUiState.Cargando -> {
                // Indicador de progreso mientras se lee de la base de datos en hilo secundario
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = stringResource(id = R.string.cargando_datos))
                    }
                }
            }
            is DetalleUiState.Exito -> {
                // Tarjeta decorada con la información detallada de la película
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = state.pelicula.titulo,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(id = R.string.director_label, state.pelicula.director),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = stringResource(id = R.string.anio_label, state.pelicula.anio),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = if (state.pelicula.esVista) {
                                stringResource(id = R.string.estado_label_vista)
                            } else {
                                stringResource(id = R.string.estado_label_pendiente)
                            },
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            is DetalleUiState.Error -> {
                // Mensaje de error si la película no existe o falla la consulta
                Text(
                    text = state.mensaje,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
