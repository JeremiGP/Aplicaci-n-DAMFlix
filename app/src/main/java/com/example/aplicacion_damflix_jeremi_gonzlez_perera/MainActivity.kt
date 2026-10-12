package com.example.aplicacion_damflix_jeremi_gonzlez_perera

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.ui.theme.Aplicacion_DAMFlix_Jeremi_González_PereraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Aplicacion_DAMFlix_Jeremi_González_PereraTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                        .verticalScroll(rememberScrollState())
                ) {
                    // PerfilUsuario()
                    FormularioPelicula()
                }
            }
        }
    }
}

@Composable
fun PerfilUsuario() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = "Avatar de usuario",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(width = 3.dp, color = Color.Gray, shape = CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = stringResource(id = R.string.nombre_usuario),
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = stringResource(id = R.string.rol),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = stringResource(id = R.string.peliculas_vistas))
            Text(text = stringResource(id = R.string.resenas))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilUsuarioPreview() {
    Aplicacion_DAMFlix_Jeremi_González_PereraTheme {
        PerfilUsuario()
    }
}

@Preview(showBackground = true)
@Composable
fun FormularioPelicula(){

    var titulo by remember { mutableStateOf("") }
    var director by remember { mutableStateOf("") }
    var fechaEstreno by remember { mutableStateOf ("")}
    var genero by remember { mutableStateOf("") }
    var pendiente by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp) // Añade separación automática
    ) {
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título de la Película") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = director,
            onValueChange = { director = it },
            label = { Text("Director de la Película") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = fechaEstreno,
            onValueChange = { fechaEstreno = it },
            label = { Text("Fecha de Estreno") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = genero,
            onValueChange = { genero = it },
            label = { Text("Género de la Película") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = pendiente,
                onCheckedChange = { pendiente = it }
            )
            Text(text = "Pendiente")
        }

        val context = LocalContext.current
        Button(

            onClick = {
                Toast.makeText(context, "Película guardada: $titulo", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = titulo.isNotBlank() && director.isNotBlank(),
        ) {
            Text(text = "Guardar")
        }
        Spacer(modifier = Modifier.height(64.dp))
    }
}







