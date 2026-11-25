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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.navigation.Ruta
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.ui.theme.Aplicacion_DAMFlix_Jeremi_González_PereraTheme
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens.DetallePeliculaCloudScreen
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens.FormularioPeliculaCloudScreen
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens.InicioScreen
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens.ListaPeliculasCloudScreen
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens.PantallaExplorarScreen
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens.PerfilUsuarioScreen
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel.ExplorarViewModel
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel.ExplorarViewModelFactory
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel.FirestorePeliculaViewModel
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel.FirestorePeliculaViewModelFactory
import kotlinx.coroutines.launch

/**
 * Actividad principal de la aplicación DAMFlix (Cloud Firestore + Retrofit).
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val app = application as DAMFlixApplication
        val firestoreFactory = FirestorePeliculaViewModelFactory(app.firestoreRepository)
        val explorarFactory = ExplorarViewModelFactory(app.firestoreRepository)

        setContent {
            Aplicacion_DAMFlix_Jeremi_González_PereraTheme {
                val firestoreViewModel: FirestorePeliculaViewModel = viewModel(factory = firestoreFactory)
                val explorarViewModel: ExplorarViewModel = viewModel(factory = explorarFactory)
                AppDAMFlixMainScreen(
                    firestoreViewModel = firestoreViewModel,
                    explorarViewModel = explorarViewModel
                )
            }
        }
    }
}

/**
 * Composable contenedor principal de la interfaz de la app.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDAMFlixMainScreen(
    firestoreViewModel: FirestorePeliculaViewModel,
    explorarViewModel: ExplorarViewModel
) {
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
                        icon = { Icon(opcion.icono, contentDescription = stringResource(opcion.labelResId)) },
                        label = { Text(stringResource(opcion.labelResId)) },
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
                startDestination = Ruta.Catalogo.path,
                modifier = Modifier.padding(innerPadding)
            ) {
                // Inicio
                composable(Ruta.Inicio.path) {
                    InicioScreen(
                        onNavigateToMovieList = { navController.navigate(Ruta.Catalogo.path) },
                        onNavigateToForm = { navController.navigate(Ruta.Formulario.path) },
                        onNavigateToProfile = { navController.navigate(Ruta.Perfil.path) }
                    )
                }

                // Catálogo Nube
                composable(Ruta.Catalogo.path) {
                    ListaPeliculasCloudScreen(
                        viewModel = firestoreViewModel,
                        onPeliculaClick = { id -> navController.navigate(Ruta.Detalle.crearRuta(id)) },
                        onEditarClick = { id -> navController.navigate(Ruta.Editar.crearRuta(id)) }
                    )
                }

                // Alta (Modo Creación)
                composable(Ruta.Formulario.path) {
                    FormularioPeliculaCloudScreen(
                        viewModel = firestoreViewModel,
                        onOperacionCompletada = {
                            navController.navigate(Ruta.Catalogo.path) {
                                popUpTo(Ruta.Formulario.path) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }

                // Edición (Modo Update completo pasando peliculaId)
                composable(
                    route = Ruta.Editar.path,
                    arguments = listOf(navArgument("peliculaId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("peliculaId") ?: ""
                    FormularioPeliculaCloudScreen(
                        peliculaId = id,
                        viewModel = firestoreViewModel,
                        onOperacionCompletada = { navController.popBackStack() }
                    )
                }

                // Detalle (Consulta por ID en Firestore)
                composable(
                    route = Ruta.Detalle.path,
                    arguments = listOf(navArgument("peliculaId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("peliculaId") ?: ""
                    DetallePeliculaCloudScreen(
                        peliculaId = id,
                        viewModel = firestoreViewModel,
                        onVolver = { navController.popBackStack() }
                    )
                }

                // Explorar API REST (Retrofit + TVMaze)
                composable(Ruta.Explorar.path) {
                    PantallaExplorarScreen(
                        viewModel = explorarViewModel,
                        onPeliculaImportada = { navController.navigate(Ruta.Catalogo.path) }
                    )
                }

                // Perfil
                composable(Ruta.Perfil.path) {
                    PerfilUsuarioScreen(
                        onNavigateBack = { volverAlInicio() }
                    )
                }
            }
        }
    }
}
