package com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.repository.PeliculaRepository
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.local.PeliculaEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel principal para gestionar el listado y el alta de películas.
 *
 * Mantiene el estado del flujo de películas observables y ejecuta las operaciones de inserción y actualización.
 *
 * @property repository Instancia del repositorio de películas.
 */
class PeliculaViewModel(private val repository: PeliculaRepository) : ViewModel() {

    /**
     * StateFlow que emite la lista de películas almacenadas en la base de datos.
     * Mantiene la suscripción activa hasta 5 segundos después de que el último observador se desconecte.
     */
    val listaPeliculas: StateFlow<List<PeliculaEntity>> = repository.todasPeliculas
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /**
     * Inserta una nueva película en el repositorio de manera asíncrona dentro del `viewModelScope`.
     */
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

    /**
     * Alterna el estado de favorita de una película dada y lo guarda en la base de datos.
     */
    fun conmutarFavorito(pelicula: PeliculaEntity) {
        viewModelScope.launch {
            val actualizada = pelicula.copy(esFavorita = !pelicula.esFavorita)
            repository.actualizar(actualizada)
        }
    }
}
