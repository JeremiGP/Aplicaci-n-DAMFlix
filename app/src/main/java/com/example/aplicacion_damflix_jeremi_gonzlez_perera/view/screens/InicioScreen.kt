package com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.R
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.ui.theme.Aplicacion_DAMFlix_Jeremi_González_PereraTheme

/**
 * Pantalla de bienvenida principal de la aplicación.
 *
 * Muestra el título de bienvenida y botones de acceso rápido para navegar al catálogo,
 * al formulario de alta o a la sección del perfil de usuario.
 *
 * @param modifier Modificadores de diseño para Compose.
 * @param onNavigateToMovieList Evento de navegación hacia el catálogo de películas.
 * @param onNavigateToForm Evento de navegación hacia el formulario de alta.
 * @param onNavigateToProfile Evento de navegación hacia la pantalla de perfil.
 */
@Composable
fun InicioScreen(
    modifier: Modifier = Modifier,
    onNavigateToMovieList: () -> Unit = {},
    onNavigateToForm: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título de bienvenida
        Text(
            text = stringResource(R.string.home_bienvenida),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subtítulo descriptivo
        Text(
            text = stringResource(R.string.home_subtitulo),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Botón para acceder al listado de películas
        Button(
            onClick = onNavigateToMovieList,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.btn_ver_listado), style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para acceder al formulario de alta de película
        Button(
            onClick = onNavigateToForm,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.btn_anadir), style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para acceder a la pantalla del perfil del usuario
        Button(
            onClick = onNavigateToProfile,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.btn_perfil), style = MaterialTheme.typography.bodyLarge)
        }
    }
}

/** Previsualización de la pantalla de inicio en Android Studio */
@Preview(showBackground = true)
@Composable
fun InicioScreenPreview() {
    Aplicacion_DAMFlix_Jeremi_González_PereraTheme {
        InicioScreen()
    }
}
