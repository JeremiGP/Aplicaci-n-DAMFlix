package com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.R
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.PeliculaCloud
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel.FirestorePeliculaViewModel

@Composable
fun ListaPeliculasCloudScreen(
    viewModel: FirestorePeliculaViewModel,
    onPeliculaClick: (String) -> Unit,
    onEditarClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val peliculas by viewModel.listaPeliculas.collectAsStateWithLifecycle()

    if (peliculas.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = stringResource(R.string.msg_catalogo_vacio))
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = peliculas, key = { it.id }) { pelicula ->
                TarjetaPeliculaCloudItem(
                    pelicula = pelicula,
                    onClick = { onPeliculaClick(pelicula.id) },
                    onToggleFavorito = { viewModel.conmutarFavorito(pelicula) },
                    onEditarClick = { onEditarClick(pelicula.id) },
                    onEliminarClick = { viewModel.borrarPelicula(pelicula.id) }
                )
            }
        }
    }
}

@Composable
fun TarjetaPeliculaCloudItem(
    pelicula: PeliculaCloud,
    onClick: () -> Unit,
    onToggleFavorito: () -> Unit,
    onEditarClick: () -> Unit,
    onEliminarClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
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
            IconButton(onClick = onToggleFavorito) {
                Icon(
                    imageVector = if (pelicula.esFavorita) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.desc_favorito),
                    tint = if (pelicula.esFavorita) Color.Red else Color.Gray
                )
            }
            IconButton(onClick = onEditarClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.desc_editar)
                )
            }
            IconButton(onClick = onEliminarClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.desc_eliminar),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
