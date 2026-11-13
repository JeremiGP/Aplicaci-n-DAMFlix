package com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.R

sealed class Ruta(val path: String, @param:StringRes val tituloRes: Int, val icono: ImageVector) {
    object Inicio : Ruta("inicio", R.string.menu_inicio, Icons.Default.Home)
    object Catalogo : Ruta("catalogo", R.string.menu_catalogo, Icons.Default.List)
    object Formulario : Ruta("alta", R.string.menu_alta, Icons.Default.Add)
    object Perfil : Ruta("perfil", R.string.menu_perfil, Icons.Default.Person)
    object Detalle : Ruta("detalle/{peliculaId}", R.string.menu_detalle, Icons.Default.Details) {
        fun crearRuta(peliculaId: Int) = "detalle/$peliculaId"
    }

    companion object {
        val opcionesMenu = listOf(Inicio, Catalogo, Formulario, Perfil)
    }
}
