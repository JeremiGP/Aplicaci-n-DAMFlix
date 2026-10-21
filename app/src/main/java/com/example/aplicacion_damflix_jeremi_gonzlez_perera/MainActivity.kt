package com.example.aplicacion_damflix_jeremi_gonzlez_perera

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.Pelicula
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.ui.screens.FormularioPeliculaScreen
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.ui.screens.ListaPeliculasScreen
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.ui.screens.PerfilUsuarioScreen
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.ui.theme.Aplicacion_DAMFlix_Jeremi_González_PereraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Aplicacion_DAMFlix_Jeremi_González_PereraTheme {
                MainContainerScreen()
            }
        }
    }
}

sealed class Screen {
    object Inicio : Screen()
    object Perfil : Screen()
    object AñadirPelicula : Screen()
    object ListaPeliculas : Screen()
}

@Composable
fun MainContainerScreen() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Inicio) }

    var listaPeliculas by remember {
        mutableStateOf(
            listOf(
                Pelicula(1, "Inception", "Christopher Nolan", "2010", "Ciencia Ficción", false, true),
                Pelicula(2, "Interstellar", "Christopher Nolan", "2014", "Ciencia Ficción", true, false),
                Pelicula(3, "The Dark Knight", "Christopher Nolan", "2008", "Acción", false, false),
            ),
        )
    }

    when (currentScreen) {
        is Screen.Inicio -> {
            HomeScreen(
                onNavigateToProfile = { currentScreen = Screen.Perfil },
                onNavigateToForm = { currentScreen = Screen.AñadirPelicula },
                onNavigateToMovieList = { currentScreen = Screen.ListaPeliculas },
            )
        }
        is Screen.Perfil -> {
            PerfilUsuarioScreen(
                onNavigateBack = { currentScreen = Screen.Inicio },
            )
        }
        is Screen.AñadirPelicula -> {
            FormularioPeliculaScreen(
                onPeliculaAdded = { nuevaPelicula ->
                    listaPeliculas = listaPeliculas + nuevaPelicula
                },
                onNavigateBack = { currentScreen = Screen.Inicio },
            )
        }
        is Screen.ListaPeliculas -> {
            ListaPeliculasScreen(
                peliculas = listaPeliculas,
                onToggleFavorite = { id ->
                    listaPeliculas = listaPeliculas.map { pelicula ->
                        if (pelicula.id == id) {
                            pelicula.copy(esFavorita = !pelicula.esFavorita)
                        } else {
                            pelicula
                        }
                    }
                },
                onNavigateBack = { currentScreen = Screen.Inicio },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToForm: () -> Unit,
    onNavigateToMovieList: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Menú Principal") },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .safeDrawingPadding()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "DAMFlix",
                style = MaterialTheme.typography.headlineLarge,
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onNavigateToMovieList,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Ver Listado de Películas")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateToForm,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Añadir Película")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateToProfile,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Ver Perfil de Usuario")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Aplicacion_DAMFlix_Jeremi_González_PereraTheme {
        HomeScreen(
            onNavigateToProfile = {},
            onNavigateToForm = {},
            onNavigateToMovieList = {},
        )
    }
}
