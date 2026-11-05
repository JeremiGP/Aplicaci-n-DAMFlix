package com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.data.DAMFlixDbHelper
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.data.PeliculaRepository
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.Pelicula
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PeliculaViewModel(private val repository: PeliculaRepository) : ViewModel() {
    private val _listaPeliculas = MutableStateFlow<List<Pelicula>>(emptyList())
    val listaPeliculas: StateFlow<List<Pelicula>> = _listaPeliculas.asStateFlow()

    init {
        cargarPeliculas()
    }

    fun cargarPeliculas() {
        viewModelScope.launch {
            _listaPeliculas.value = repository.obtenerTodas()
        }
    }

    fun agregarPelicula(
        titulo: String,
        director: String,
        fechaEstreno: String,
        genero: String,
        pendiente: Boolean,
        esVista: Boolean
    ) {
        viewModelScope.launch {
            val nueva = Pelicula(
                titulo = titulo,
                director = director,
                fechaEstreno = fechaEstreno,
                genero = genero,
                pendiente = pendiente,
                esVista = esVista
            )
            repository.insertar(nueva)
            cargarPeliculas()
        }
    }

    fun conmutarFavorito(pelicula: Pelicula) {
        viewModelScope.launch {
            val actualizada = pelicula.copy(esFavorita = !pelicula.esFavorita)
            repository.actualizar(actualizada)
            cargarPeliculas()
        }
    }

    companion object {
        fun provideFactory(context: Context): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dbHelper = DAMFlixDbHelper(context.applicationContext)
                val repository = PeliculaRepository(dbHelper)
                return PeliculaViewModel(repository) as T
            }
        }
    }
}
