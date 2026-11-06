package com.example.aplicacion_damflix_jeremi_gonzlez_perera

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.navigation.Ruta
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.ui.theme.Aplicacion_DAMFlix_Jeremi_González_PereraTheme
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens.FormularioAgregarPeliculaScreen
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens.InicioScreen
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens.ListaPeliculasScreen
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens.PerfilUsuarioScreen
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel.PeliculaViewModel
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel.PeliculaViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val app = application as DAMFlixApplication
        val factory = PeliculaViewModelFactory(app.repository)
        setContent {
            Aplicacion_DAMFlix_Jeremi_González_PereraTheme {
                val viewModel: PeliculaViewModel = viewModel(factory = factory)
                AppDAMFlixMainScreen(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDAMFlixMainScreen(viewModel: PeliculaViewModel) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route
    val opcionesMenu = Ruta.opcionesMenu

    fun volverAlInicio() {
        val popped = navController.popBackStack(Ruta.Inicio.path, inclusive = false)
        if (!popped) {
            navController.navigate(Ruta.Inicio.path) {
                popUpTo(Ruta.Inicio.path) { inclusive = true }
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = stringResource(R.string.drawer_titulo),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))
                opcionesMenu.forEach { opcion ->
                    NavigationDrawerItem(
                        icon = { Icon(opcion.icono, contentDescription = stringResource(opcion.tituloRes)) },
                        label = { Text(stringResource(opcion.tituloRes)) },
                        selected = rutaActual == opcion.path,
                        onClick = {
                            coroutineScope.launch { drawerState.close() }
                            if (rutaActual != opcion.path) {
                                if (opcion.path == Ruta.Inicio.path) {
                                    volverAlInicio()
                                } else {
                                    navController.navigate(opcion.path) {
                                        popUpTo(Ruta.Inicio.path) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.top_bar_titulo), fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = stringResource(R.string.abrir_menu))
                        }
                    },
                    actions = {
                        if (rutaActual != Ruta.Inicio.path) {
                            IconButton(onClick = { volverAlInicio() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = stringResource(R.string.btn_volver)
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Ruta.Inicio.path,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Ruta.Inicio.path) {
                    InicioScreen(
                        onNavigateToMovieList = { navController.navigate(Ruta.Catalogo.path) },
                        onNavigateToForm = { navController.navigate(Ruta.Formulario.path) },
                        onNavigateToProfile = { navController.navigate(Ruta.Perfil.path) }
                    )
                }
                composable(Ruta.Catalogo.path) {
                    ListaPeliculasScreen(
                        viewModel = viewModel,
                        onNavigateBack = { volverAlInicio() }
                    )
                }
                composable(Ruta.Formulario.path) {
                    FormularioAgregarPeliculaScreen(
                        viewModel = viewModel,
                        onPeliculaGuardada = {
                            navController.navigate(Ruta.Catalogo.path) {
                                popUpTo(Ruta.Inicio.path) { saveState = true }
                            }
                        },
                        onNavigateBack = { volverAlInicio() }
                    )
                }
                composable(Ruta.Perfil.path) {
                    PerfilUsuarioScreen(
                        onNavigateBack = { volverAlInicio() }
                    )
                }
            }
        }
    }
}
