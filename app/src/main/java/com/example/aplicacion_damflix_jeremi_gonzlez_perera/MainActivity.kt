package com.example.aplicacion_damflix_jeremi_gonzlez_perera

import android.os.Bundle
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
                PerfilUsuario()
            }
        }
    }
}

@Composable
fun PerfilUsuario() {
    Column(
        modifier = Modifier
            .fillMaxSize()
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





