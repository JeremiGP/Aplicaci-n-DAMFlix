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

/**
 * Clase sellada que define los destinos de navegación de la aplicación.
 *
 * Cada destino especifica su ruta ([path]), el recurso de título traducible ([tituloRes])
 * y el icono decorativo asociado ([icono]).
 */
sealed class Ruta(val path: String, @param:StringRes val tituloRes: Int, val icono: ImageVector) {
    /** Pantalla principal o de bienvenida */
    object Inicio : Ruta("inicio", R.string.menu_inicio, Icons.Default.Home)

    /** Pantalla del catálogo de películas */
    object Catalogo : Ruta("catalogo", R.string.menu_catalogo, Icons.Default.List)

    /** Pantalla para agregar una nueva película */
    object Formulario : Ruta("alta", R.string.menu_alta, Icons.Default.Add)

    /** Pantalla con la información del perfil del usuario */
    object Perfil : Ruta("perfil", R.string.menu_perfil, Icons.Default.Person)

    /**
     * Pantalla de detalle con argumento dinámico (peliculaId).
     */
    object Detalle : Ruta("detalle/{peliculaId}", R.string.menu_detalle, Icons.Default.Details) {
        /** Construye la ruta concreta asignando el valor del ID recibido */
        fun crearRuta(peliculaId: Int) = "detalle/$peliculaId"
    }

    companion object {
        /** Lista de opciones visibles en el menú lateral de navegación (Drawer) */
        val opcionesMenu = listOf(Inicio, Catalogo, Formulario, Perfil)
    }
}
