package com.example.aplicacion_damflix_jeremi_gonzlez_perera.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.R
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel.PeliculaViewModel

/**
 * Pantalla con formulario de alta para registrar una nueva película en Room.
 *
 * Incluye validación de campos obligatorios (título y director) para habilitar el botón de guardado.
 *
 * @param viewModel Instancia de [PeliculaViewModel] para guardar la película.
 * @param modifier Modificador para Compose.
 * @param onPeliculaGuardada Callback invocado tras guardar la película con éxito.
 * @param onNavigateBack Callback para volver al inicio.
 */
@Composable
fun FormularioAgregarPeliculaScreen(
    viewModel: PeliculaViewModel,
    modifier: Modifier = Modifier,
    onPeliculaGuardada: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    // Estados locales para controlar los campos de entrada del formulario
    var titulo by remember { mutableStateOf("") }
    var director by remember { mutableStateOf("") }
    var anioTexto by remember { mutableStateOf("") }
    var fechaEstreno by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var pendiente by remember { mutableStateOf(false) }
    var esVista by remember { mutableStateOf(false) }

    // Validación básica: exige título y director no vacíos
    val esFormularioValido = titulo.isNotBlank() && director.isNotBlank()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.anadir_pelicula),
            style = MaterialTheme.typography.headlineMedium
        )

        // Campo: Título de la película
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text(stringResource(R.string.titulo_pelicula)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Campo: Director o Directora
        OutlinedTextField(
            value = director,
            onValueChange = { director = it },
            label = { Text(stringResource(R.string.director_pelicula)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Campo: Año de estreno
        OutlinedTextField(
            value = anioTexto,
            onValueChange = { anioTexto = it },
            label = { Text(stringResource(R.string.anio_estreno)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo: Fecha de estreno en formato texto
        OutlinedTextField(
            value = fechaEstreno,
            onValueChange = { fechaEstreno = it },
            label = { Text(stringResource(R.string.fecha_estreno)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Campo: Género cinematográfico
        OutlinedTextField(
            value = genero,
            onValueChange = { genero = it },
            label = { Text(stringResource(R.string.genero_pelicula)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Casilla de verificación: Película pendiente
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(checked = pendiente, onCheckedChange = { pendiente = it })
            Text(text = stringResource(R.string.pendiente))
        }

        // Casilla de verificación: Película vista
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(checked = esVista, onCheckedChange = { esVista = it })
            Text(text = stringResource(R.string.ya_vista))
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón de guardar entrada
        Button(
            onClick = {
                val anio = anioTexto.toIntOrNull() ?: 0
                viewModel.agregarPelicula(
                    titulo = titulo,
                    director = director,
                    anio = anio,
                    fechaEstreno = fechaEstreno,
                    genero = genero,
                    pendiente = pendiente,
                    esVista = esVista
                )
                // Limpia el formulario tras guardar
                titulo = ""
                director = ""
                anioTexto = ""
                fechaEstreno = ""
                genero = ""
                pendiente = false
                esVista = false
                onPeliculaGuardada()
            },
            enabled = esFormularioValido,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.btn_accept))
        }

        // Botón para cancelar y regresar
        OutlinedButton(
            onClick = onNavigateBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.btn_volver_inicio))
        }
    }
}
