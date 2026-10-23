package com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.Pelicula

sealed class Screen {
    object Inicio : Screen()
    object Perfil : Screen()
    object AñadirPelicula : Screen()
    object ListaPeliculas : Screen()
}

class MainViewModel : ViewModel() {
    var currentScreen by mutableStateOf<Screen>(Screen.Inicio)
        private set

    var listaPeliculas by mutableStateOf(
        listOf(
            Pelicula(1, "Inception", "Christopher Nolan", "2010", "Ciencia Ficción", false, true),
            Pelicula(2, "Interstellar", "Christopher Nolan", "2014", "Ciencia Ficción", true, false),
            Pelicula(3, "The Dark Knight", "Christopher Nolan", "2008", "Acción", false, false),
        ),
    )
        private set

    fun navigateTo(screen: Screen) {
        currentScreen = screen
    }

    fun addPelicula(pelicula: Pelicula) {
        listaPeliculas = listaPeliculas + pelicula
    }

    fun toggleFavorite(id: Int) {
        listaPeliculas = listaPeliculas.map { pelicula ->
            if (pelicula.id == id) {
                pelicula.copy(esFavorita = !pelicula.esFavorita)
            } else {
                pelicula
            }
        }
    }
}
