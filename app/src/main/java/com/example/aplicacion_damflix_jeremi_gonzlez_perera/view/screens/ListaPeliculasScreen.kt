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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.Pelicula
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.ui.theme.Aplicacion_DAMFlix_Jeremi_González_PereraTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaPeliculasScreen(
    peliculas: List<Pelicula>,
    onToggleFavorite: (Int) -> Unit,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Listado de Películas") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        if (peliculas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "No hay películas registradas.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    items = peliculas,
                    key = { pelicula -> pelicula.id },
                ) { pelicula ->
                    PeliculaItemCard(
                        pelicula = pelicula,
                        onToggleFavorite = { onToggleFavorite(pelicula.id) },
                    )
                }
            }
        }
    }
}

@Composable
fun PeliculaItemCard(
    pelicula: Pelicula,
    onToggleFavorite: () -> Unit,
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = pelicula.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Director: ${pelicula.director}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${pelicula.genero} • ${pelicula.fechaEstreno}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                if (pelicula.pendiente) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Pendiente de ver",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }

            IconButton(onClick = onToggleFavorite) {
                Icon(
                    imageVector = if (pelicula.esFavorita) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (pelicula.esFavorita) "Marcar como no favorita" else "Marcar como favorita",
                    tint = if (pelicula.esFavorita) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListaPeliculasScreenPreview() {
    val samplePeliculas = listOf(
        Pelicula(1, "Inception", "Christopher Nolan", "2010", "Ciencia Ficción", false, true),
        Pelicula(2, "Interstellar", "Christopher Nolan", "2014", "Ciencia Ficción", true, false),
    )
    Aplicacion_DAMFlix_Jeremi_González_PereraTheme {
        ListaPeliculasScreen(
            peliculas = samplePeliculas,
            onToggleFavorite = {},
            onNavigateBack = {},
        )
    }
}
