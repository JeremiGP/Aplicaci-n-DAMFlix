package com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.R
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.local.PeliculaEntity
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel.PeliculaViewModel

/**
 * Pantalla que muestra el catálogo de películas en una [LazyColumn].
 *
 * Se suscribe de forma reactiva a la lista expuesta por el [PeliculaViewModel].
 * Aplica el patrón de elevación de eventos (Event Hoisting) notificando mediante callbacks
 * la navegación al detalle y los cambios en el estado de favoritos.
 *
 * @param viewModel ViewModel de la lista de películas.
 * @param modifier Modificador para Compose.
 * @param onNavigateBack Callback para regresar al inicio.
 * @param onPeliculaClick Callback elevado para navegar al detalle de la película seleccionada pasando su ID.
 */
@Composable
fun ListaPeliculasScreen(
    viewModel: PeliculaViewModel,
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    onPeliculaClick: (Int) -> Unit = {}
) {
    // Recoge la lista de películas reactivamente respetando el ciclo de vida de la pantalla
    val peliculas by viewModel.listaPeliculas.collectAsStateWithLifecycle()

    if (peliculas.isEmpty()) {
        // Vista vacía si no hay películas registradas en Room
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.no_peliculas),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Button(onClick = onNavigateBack) {
                    Text(text = stringResource(R.string.btn_volver_inicio))
                }
            }
        }
    } else {
        // Renderizado optimizado del catálogo usando LazyColumn
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = peliculas,
                key = { p -> p.id }
            ) { pelicula ->
                TarjetaPeliculaItem(
                    pelicula = pelicula,
                    onToggleFavorito = { viewModel.conmutarFavorito(pelicula) },
                    onPeliculaClick = { onPeliculaClick(pelicula.id) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onNavigateBack,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.btn_volver_inicio))
                }
            }
        }
    }
}

/**
 * Composable que dibuja la tarjeta de cada película individual en el catálogo.
 *
 * @param pelicula Datos de la entidad [PeliculaEntity].
 * @param onToggleFavorito Evento para marcar/desmarcar como favorita.
 * @param onPeliculaClick Evento pulsación sobre la tarjeta para abrir la vista de detalle.
 */
@Composable
fun TarjetaPeliculaItem(
    pelicula: PeliculaEntity,
    onToggleFavorito: () -> Unit,
    onPeliculaClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onPeliculaClick
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = pelicula.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                val detalleAnio = if (pelicula.anio > 0) pelicula.anio.toString() else pelicula.fechaEstreno
                Text(
                    text = "${pelicula.director} • $detalleAnio",
                    style = MaterialTheme.typography.bodySmall
                )
                if (pelicula.genero.isNotBlank()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = pelicula.genero,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                if (pelicula.pendiente) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = stringResource(R.string.pendiente),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                if (pelicula.esVista) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = stringResource(R.string.ya_vista),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            // Botón icono para marcar/desmarcar como favorita
            IconButton(onClick = onToggleFavorito) {
                Icon(
                    imageVector = if (pelicula.esFavorita) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.marcar_favorito),
                    tint = if (pelicula.esFavorita) Color.Red else Color.Gray
                )
            }
        }
    }
}
