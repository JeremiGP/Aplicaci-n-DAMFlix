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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.ui.theme.Aplicacion_DAMFlix_Jeremi_González_PereraTheme
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens.FormularioPeliculaScreen
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens.ListaPeliculasScreen
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens.PerfilUsuarioScreen
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel.MainViewModel

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

sealed class Ruta(val path: String, val label: String, val icon: ImageVector) {
    object Inicio : Ruta(
        path = "inicio",
        label = "Inicio",
        icon = Icons.Filled.Home,
    )
    object Listado : Ruta(
        path = "listado",
        label = "Listado",
        icon = Icons.Default.List,
    )
    object Formulario : Ruta(
        path = "alta",
        label = "Añadir",
        icon = Icons.Filled.Add,
    )
    object Perfil : Ruta(
        path = "perfil",
        label = "Perfil",
        icon = Icons.Filled.Person,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContainerScreen(viewModel: MainViewModel = viewModel()) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("DAMFlix", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
            )
        },
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val rutaActual = navBackStackEntry?.destination?.route
            NavigationBar {
                listOf(
                    Ruta.Inicio,
                    Ruta.Listado,
                    Ruta.Formulario,
                    Ruta.Perfil,
                ).forEach { ruta ->
                    NavigationBarItem(
                        selected = rutaActual == ruta.path,
                        onClick = {
                            if (rutaActual != ruta.path) {
                                navController.navigate(ruta.path) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = { Icon(ruta.icon, contentDescription = ruta.label) },
                        label = { Text(ruta.label) },
                    )
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Ruta.Inicio.path,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(Ruta.Inicio.path) {
                HomeScreen(
                    onNavigateToMovieList = { navController.navigate(Ruta.Listado.path) },
                    onNavigateToForm = { navController.navigate(Ruta.Formulario.path) },
                    onNavigateToProfile = { navController.navigate(Ruta.Perfil.path) },
                )
            }
            composable(Ruta.Listado.path) {
                ListaPeliculasScreen(
                    peliculas = viewModel.listaPeliculas,
                    onToggleFavorite = { id -> viewModel.toggleFavorite(id) },
                    onNavigateBack = { navController.popBackStack() },
                )
            }
            composable(Ruta.Formulario.path) {
                FormularioPeliculaScreen(
                    onPeliculaAdded = { nuevaPelicula -> viewModel.addPelicula(nuevaPelicula) },
                    onNavigateBack = { navController.popBackStack() },
                )
            }
            composable(Ruta.Perfil.path) {
                PerfilUsuarioScreen(
                    onNavigateBack = { navController.popBackStack() },
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToForm: () -> Unit,
    onNavigateToMovieList: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Bienvenido a DAMFlix",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Tu gestor personal de películas",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onNavigateToMovieList,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
        ) {
            Text(text = "Ver Listado de Películas", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateToForm,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
        ) {
            Text(text = "Añadir Película", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateToProfile,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
        ) {
            Text(text = "Ver Perfil de Usuario", style = MaterialTheme.typography.bodyLarge)
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
