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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.ui.theme.Aplicacion_DAMFlix_Jeremi_González_PereraTheme
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens.FormularioPeliculaScreen
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens.ListaPeliculasScreen
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens.PerfilUsuarioScreen
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel.MainViewModel
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel.Screen

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

@Composable
fun MainContainerScreen(viewModel: MainViewModel = viewModel()) {
    when (viewModel.currentScreen) {
        is Screen.Inicio -> {
            HomeScreen(
                onNavigateToProfile = { viewModel.navigateTo(Screen.Perfil) },
                onNavigateToForm = { viewModel.navigateTo(Screen.AñadirPelicula) },
                onNavigateToMovieList = { viewModel.navigateTo(Screen.ListaPeliculas) },
            )
        }
        is Screen.Perfil -> {
            PerfilUsuarioScreen(
                onNavigateBack = { viewModel.navigateTo(Screen.Inicio) },
            )
        }
        is Screen.AñadirPelicula -> {
            FormularioPeliculaScreen(
                onPeliculaAdded = { nuevaPelicula ->
                    viewModel.addPelicula(nuevaPelicula)
                },
                onNavigateBack = { viewModel.navigateTo(Screen.Inicio) },
            )
        }
        is Screen.ListaPeliculas -> {
            ListaPeliculasScreen(
                peliculas = viewModel.listaPeliculas,
                onToggleFavorite = { id ->
                    viewModel.toggleFavorite(id)
                },
                onNavigateBack = { viewModel.navigateTo(Screen.Inicio) },
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
