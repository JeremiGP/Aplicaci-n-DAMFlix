package com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.PeliculaRepository
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.local.PeliculaEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PeliculaViewModel(private val repository: PeliculaRepository) : ViewModel() {
    val listaPeliculas: StateFlow<List<PeliculaEntity>> = repository.todasPeliculas
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun agregarPelicula(
        titulo: String,
        director: String,
        anio: Int,
        fechaEstreno: String,
        genero: String,
        pendiente: Boolean,
        esVista: Boolean
    ) {
        viewModelScope.launch {
            val nueva = PeliculaEntity(
                titulo = titulo,
                director = director,
                anio = anio,
                fechaEstreno = fechaEstreno,
                genero = genero,
                pendiente = pendiente,
                esVista = esVista
            )
            repository.insertar(nueva)
        }
    }

    fun conmutarFavorito(pelicula: PeliculaEntity) {
        viewModelScope.launch {
            val actualizada = pelicula.copy(esFavorita = !pelicula.esFavorita)
            repository.actualizar(actualizada)
        }
    }
}
